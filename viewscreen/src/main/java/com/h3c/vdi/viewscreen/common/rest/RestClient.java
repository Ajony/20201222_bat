package com.h3c.vdi.viewscreen.common.rest;

/**
 * Created by x19765 on 2020/10/19.
 */


import com.google.common.base.Charsets;
import com.h3c.vdi.viewscreen.common.rest.ex.RestTemplateErrorHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.Closeable;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * REST API客户端，目前仅支持摘要认证。
 */
public class RestClient implements Closeable {

    /** 登录客户端 */
    private CloseableHttpClient client = null;

    /** Spring 封装的进行Rest交互的模板工具类 */
    private VdiRestTemplate restTemplate = null;

    /** 日志 */
    private Log log = LogFactory.getLog(getClass());

    public RestClient(String protocol, String host, int port, String username,
                      String password) {
        this(protocol, host, port, username, password, 100, 100);
    }

    public RestClient(String protocol, String host, int port, String username,
                      String password, int maxTotalConnections, final int perRouteConnections) {
        protocol = StringUtils.trimToNull(protocol);
        if (protocol == null) {
            throw new IllegalArgumentException("invalid protocol");
        }
        protocol = protocol.toLowerCase();
        host = StringUtils.trimToNull(host);
        if (host == null) {
            throw new IllegalArgumentException("invalid host");
        }
        username = StringUtils.trimToNull(username);

        //Client Pool
        PoolingHttpClientConnectionManager ccm = initConnectionPool(host, port, maxTotalConnections, perRouteConnections);

        // 认证信息
        CredentialsProvider credentialsProvider = initCredentialsProvider(host, port, username, password);

        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                .setSocketTimeout(120000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        client = HttpClients.custom()
                .setConnectionManager(ccm)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();


        restTemplate = new VdiRestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        restTemplate.getMessageConverters().add(new Jaxb2CollectionHttpMessageConverter());
        restTemplate.getMessageConverters().add(new Jaxb2RootElementHttpMessageConverter());
        restTemplate.setUriTemplateHandler(initUriTemplateHandler(protocol, host, port));
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        restTemplate.getInterceptors().add(new AcceptHeaderInterceptor());
    }

    private CredentialsProvider initCredentialsProvider(String host, int port, String username, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if (username != null && password != null) {
            credentialsProvider.setCredentials(
                    new AuthScope(host, port, null),
                    new UsernamePasswordCredentials(username, password));
        }
        return credentialsProvider;
    }

    private PoolingHttpClientConnectionManager initConnectionPool(String host, int port, int maxTotalConnections, int perRouteConnections) {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(initSSLContext(), NoopHostnameVerifier.INSTANCE))
                .build();

        PoolingHttpClientConnectionManager ccm = new PoolingHttpClientConnectionManager(registry);

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoTimeout(600000)// 读取数据超时
                .setSoReuseAddress(true)// 允许地址复用
                .setRcvBufSize(60000)// socket缓冲
                .setSndBufSize(60000)
                .build();
        ccm.setDefaultSocketConfig(socketConfig);
        ccm.setSocketConfig(new HttpHost(host, port), socketConfig);
        ccm.setMaxTotal(maxTotalConnections);
        ccm.setDefaultMaxPerRoute(perRouteConnections);
        ccm.setMaxPerRoute(new HttpRoute(new HttpHost(host, port)), perRouteConnections);
        return ccm;
    }

    private SSLContext initSSLContext() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
            return sc;
        } catch (Exception e) {
            log.warn(null, e);
            throw new RuntimeException("Can not init SSLContext");
        }
    }

    private UriTemplateHandler initUriTemplateHandler(String protocol, String host, int port) {
        String baseUri = protocol + "://" + host + ":" + port;
        return new DefaultUriBuilderFactory(baseUri);
    }

    /**
     * 获取当前HttpClient对应的RestTemplate
     *
     * @return 当前HttpClient对应的RestTemplate
     */
    public VdiRestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * 退出时关闭。
     */
    @Override
    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
            log.warn("client close error", e);
        }
    }

    /** 信任任何内容的 TrustManager。 */
    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        /**
         * 检查客户端信任证书
         *
         * @param arg0 证书
         * @param arg1 类型
         * @throws java.security.cert.CertificateException 证书异常
         */
        @Override
        public void checkClientTrusted(
                X509Certificate[] arg0, String arg1)
                throws java.security.cert.CertificateException {
        }

        /**
         * 检查服务器信任证书
         *
         * @param arg0 证书
         * @param arg1 类型
         * @throws java.security.cert.CertificateException 证书异常
         */
        @Override
        public void checkServerTrusted(
                X509Certificate[] arg0, String arg1)
                throws java.security.cert.CertificateException {
        }
    }

    /**
     * 自定义REST Template Request Accept Header类型
     */
    private static class AcceptHeaderInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().setAccept(acceptHeaders());
            return execution.execute(request, body);
        }

        private List<MediaType> acceptHeaders() {
            List<MediaType> types = new ArrayList<>();
            types.add(new MediaType("application", "xml", Charsets.UTF_8));
            types.add(new MediaType("application", "json", Charsets.UTF_8));
            return types;
        }
    }
}
