package com.h3c.vdi.viewscreen.utils.httpclient;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseAdCode;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseInverse;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseLocation;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author lgq
 * @since 2020/7/8 14:21
 */

//todo 正规途径，我们需要将证书导入到密钥库中，现在我们采取另外一种方式：绕过https证书认证实现访问。
@Log4j2
public class HttpProxyUtil {

    //todo 绕过验证 地理编码
    public static ApiResponse<ResponseLocation> addressLongitude(String address) {
        ApiResponse<ResponseLocation> returnValue = null;

        //地理/逆地理编码
        String url = HttpConstant.ADDRESS_TO_LONGITUDE_URL + "&key=" + HttpConstant.KEY + "&address=" + address;

        log.info("请求url:" + url);

        //采用绕过验证的方式处理https请求
        SSLContext sslContext = createIgnoreVerifySSL();

        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HttpConstant.HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(HttpConstant.HTTPS, new SSLConnectionSocketFactory(sslContext))
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        //todo 代理地址 host port http
        HttpHost proxy = new HttpHost(HttpConstant.HOSTNAME, HttpConstant.PORT, HttpConstant.HTTP);

        //todo 两种HTTP Proxy认证方式
        /**
         *  1：
         *  Authenticator 类是一个抽象类，必须先定义一个类来继承它，
         *  然后重写它的 getPasswordAuthentication() 这个方法，
         *  定义新类比较繁琐，我们可以直接使用匿名类
         */
       /* Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HttpConstant.USERNAME, HttpConstant.PASSWORD.toCharArray());
            }
        };
        Authenticator.setDefault(authenticator);*/


        /**
         *  2：我们使用第2种来实现proxy认证
         *  HttpClient 也提供了一个类 CredentialsProvider 来实现 HTTP 的身份认证，
         *  它的子类BasicCredentialsProvider 用于基本身份认证。
         *  和 Authenticator 不一样的是，这种方法不再是全局的，而是针对指定的 HttpClient 实例有效，可以根据需要来设置
         */
        //todo  设置HTTP 407 Proxy Authentication Required
        CredentialsProvider provider = new BasicCredentialsProvider();  //// 设置BasicAuth
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(HttpConstant.USERNAME, HttpConstant.PASSWORD));    //设置凭证

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)                      //设置连接超时时间，单位毫秒
                .setConnectionRequestTimeout(1000)            //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setSocketTimeout(5000).build();              //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        try (CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                //todo Set the default credentials provider
                .setDefaultCredentialsProvider(provider)
                //todo 设置连接管理器
                .setConnectionManager(httpClientConnectionManager)
                .setProxy(proxy)   //设置代理
                .build()) {
            HttpGet httpGet = new HttpGet(url);// 创建一个get请求
            CloseableHttpResponse chr = client.execute(httpGet);       // 用http连接去执行get请求并且获得http响应
            if (chr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = chr.getEntity();                       // 从response中取到响实体
                String data = EntityUtils.toString(entity);                     // 把响应实体转成文本
                log.info("返回信息经纬度 success ：" + data);

                String json = data.replaceAll("\\[]", HttpConstant.NULL);    // JSON转对象
                ResponseLocation responseLocation = new Gson().fromJson(json, ResponseLocation.class);
                returnValue = ApiResponse.buildSuccess(responseLocation);
            }
            chr.close();
            return returnValue;
        } catch (IOException e) {
            log.error("地理编码异常", e);
            return null;
        }
    }

    @SneakyThrows
    private static SSLContext createIgnoreVerifySSL() {
        SSLContext sc = SSLContext.getInstance(HttpConstant.SSLV3);

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static String getLocation(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        ApiResponse<ResponseLocation> apiResponse = addressLongitude(address);
//        return Optional.ofNullable(apiResponse)
//                .map(ApiResponse::getData)
//                .map(ResponseLocation::getGeocodes)
//                .map(c -> c.get(0).getLocation())
//                .orElse(null);
        if (null != apiResponse && null != apiResponse.getData()) {
            List<ResponseLocation.GeocodesBean> geoCodes = apiResponse.getData().getGeocodes();
            if (CollectionUtils.isNotEmpty(geoCodes)) {
                if (null != geoCodes.get(0).getLocation()) return geoCodes.get(0).getLocation();
            }
        }
        return null;
    }


    //todo 获取全国adCode
    public static ApiResponse<ResponseAdCode> adCode(String address) {
        ApiResponse<ResponseAdCode> returnValue = null;

        String adcode = HttpConstant.ADDRESS_UPD + "&key=" + HttpConstant.KEY + "&keywords=" + address;

        log.info("请求url:" + adcode);

        SSLContext sslContext = createIgnoreVerifySSL();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HttpConstant.HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(HttpConstant.HTTPS, new SSLConnectionSocketFactory(sslContext))
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        HttpHost proxy = new HttpHost(HttpConstant.HOSTNAME, HttpConstant.PORT, HttpConstant.HTTP);

        CredentialsProvider provider = new BasicCredentialsProvider();  //// 设置BasicAuth
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(HttpConstant.USERNAME, HttpConstant.PASSWORD));    //设置凭证

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)                      //设置连接超时时间，单位毫秒
                .setConnectionRequestTimeout(1000)            //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setSocketTimeout(5000).build();              //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        try (CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCredentialsProvider(provider)
                .setConnectionManager(httpClientConnectionManager)
                .setProxy(proxy)   //设置代理
                .build()) {
            HttpGet httpGet = new HttpGet(adcode);// 创建一个get请求
            CloseableHttpResponse chr = client.execute(httpGet);       // 用http连接去执行get请求并且获得http响应
            if (chr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = chr.getEntity();                       // 从response中取到响实体
                String data = EntityUtils.toString(entity);                     // 把响应实体转成文本
                log.info("返回信息 adCode success ：" + data);

                String json = data.replaceAll("\\[]", HttpConstant.NULL); // JSON转对象
                ResponseAdCode var = new Gson().fromJson(json, ResponseAdCode.class);
                returnValue = ApiResponse.buildSuccess(var);
            }
            chr.close();
            return returnValue;
        } catch (IOException e) {
            log.error("adCode 异常..............", e);
            return null;
        }
    }





    //todo 逆地理编码
    public static ApiResponse<ResponseInverse> locationToAddress(String location) {
        ApiResponse<ResponseInverse> returnValue = null;

        //地理/逆地理编码
        String url = HttpConstant.LOCATION_TO_ADDRESS_URL + "&key=" + HttpConstant.KEY + "&location=" + location;

        log.info("请求url:" + url);

        //采用绕过验证的方式处理https请求
        SSLContext sslContext = createIgnoreVerifySSL();

        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HttpConstant.HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(HttpConstant.HTTPS, new SSLConnectionSocketFactory(sslContext))
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        HttpHost proxy = new HttpHost(HttpConstant.HOSTNAME, HttpConstant.PORT, HttpConstant.HTTP);

        CredentialsProvider provider = new BasicCredentialsProvider();  //// 设置BasicAuth
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(HttpConstant.USERNAME, HttpConstant.PASSWORD));    //设置凭证

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)                      //设置连接超时时间，单位毫秒
                .setConnectionRequestTimeout(1000)            //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setSocketTimeout(5000).build();              //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        try (CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCredentialsProvider(provider)
                .setConnectionManager(httpClientConnectionManager)
                .setProxy(proxy)   //设置代理
                .build()) {
            HttpGet httpGet = new HttpGet(url);// 创建一个get请求
            CloseableHttpResponse chr = client.execute(httpGet);       // 用http连接去执行get请求并且获得http响应
            if (chr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = chr.getEntity();                       // 从response中取到响实体
                String data = EntityUtils.toString(entity);                     // 把响应实体转成文本
                log.info("返回信息逆地理编码 success ：" + data);

                String json = data.replaceAll("\\[]", HttpConstant.NULL);    // JSON转对象
                ResponseInverse responseInverse = new Gson().fromJson(json, ResponseInverse.class);
                returnValue = ApiResponse.buildSuccess(responseInverse);
            }
            chr.close();
            return returnValue;
        } catch (IOException e) {
            log.error("逆地理编码异常", e);
            return null;
        }
    }

}
