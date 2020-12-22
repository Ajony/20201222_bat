package com.h3c.vdi.viewscreen.utils.common.cloud;

import com.h3c.vdi.viewscreen.constant.Constant;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;


@Component
@ConditionalOnProperty(name = "vdi.service.provider.viewscreen", havingValue = Constant.EnvPath.CLOUD_ON)
@Log4j2
public class CloudRestClient {

    @Resource
    private RestTemplate restTemplate;

    @Value("${cloud.gateway.kong.url}")
    private String gatewayServer;

    @Value("${cloud.gateway.kong.port}")
    private int gatewayPort;

    /**
     * 基础模板，直接套用restTemplate
     *
     * @param url
     * @param method
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          Class<T> responseType) {
        return restTemplate.exchange(accessUrl(url), method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(accessUrl(url), method, requestEntity, responseType);
    }

    /**
     * get相关方法
     *
     * @param url
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType) {
        HttpEntity<String> entity = buildHttpEntity(headers, null);
        return get(url, entity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        HttpHeaders headers = commonHeader();
        headers.remove(HttpHeaders.CONTENT_TYPE);
        return get(url, headers, responseType);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, Object> params) {
        return restTemplate.getForEntity(url, responseType, params);
    }

    public <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = buildHttpEntity(headers, null);
        return get(url, entity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = commonHeader();
        headers.remove(HttpHeaders.CONTENT_TYPE);
        return get(url, headers, responseType);
    }

    /**
     * post相关方法
     *
     * @param url
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, String body, Class<T> responseType) {
        return post(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> post(String url, String body, Class<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return post(url, entity, responseType);
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, String body, ParameterizedTypeReference<T> responseType) {
        return post(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> post(String url, String body, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return post(url, entity, responseType);
    }

    /**
     * put相关方法
     *
     * @param url
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> put(String url, HttpHeaders headers, String body, Class<T> responseType) {
        return put(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> put(String url, String body, Class<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return put(url, entity, responseType);
    }

    public <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> put(String url, HttpHeaders headers, String body, ParameterizedTypeReference<T> responseType) {
        return put(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> put(String url, String body, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return put(url, entity, responseType);
    }

    /**
     * delete相关方法
     *
     * @param url
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, String body, Class<T> responseType) {
        return delete(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> delete(String url, String body, Class<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return delete(url, entity, responseType);
    }

    public <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, String body, ParameterizedTypeReference<T> responseType) {
        return delete(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> delete(String url, String body, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return delete(url, entity, responseType);
    }

    /**
     * patch相关方法
     *
     * @param url
     * @param requestEntity
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> patch(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return exchange(url, HttpMethod.PATCH, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> patch(String url, HttpHeaders headers, String body, Class<T> responseType) {
        return patch(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> patch(String url, String body, Class<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return patch(url, entity, responseType);
    }

    public <T> ResponseEntity<T> patch(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.PATCH, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> patch(String url, HttpHeaders headers, String body, ParameterizedTypeReference<T> responseType) {
        return patch(url, new HttpEntity(body, headers), responseType);
    }

    public <T> ResponseEntity<T> patch(String url, String body, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = buildHttpEntityByBody(body);
        return patch(url, entity, responseType);
    }

    /**
     * 根据body构建通用HttpEntity
     *
     * @param body
     * @return
     */
    private HttpEntity<String> buildHttpEntityByBody(String body) {
        HttpHeaders headers = commonHeader();
        if (StringUtils.isBlank(body)) {
            headers.remove(HttpHeaders.CONTENT_TYPE);
        }
        return buildHttpEntity(headers, body);
    }

    /**
     * 根据body与Header构建通用HttpEntity
     *
     * @param headers
     * @param body
     * @return
     */
    public HttpEntity<String> buildHttpEntity(HttpHeaders headers, String body) {
        HttpHeaders newHeaders = new HttpHeaders();
        headers.forEach((name, value) -> {
            if (CollectionUtils.isNotEmpty(value)) {
                newHeaders.put(name, value);
            }
        });

        HttpEntity<String> entity = StringUtils.isNotBlank(body) ?
                new HttpEntity<>(body, newHeaders) : new HttpEntity<>(newHeaders);
        return entity;
    }

    /**
     * 通用header构建
     *
     * @return
     */
    public HttpHeaders commonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        //添加通过kong网关所必须的token
        headers.add(Constant.Cloudos.CLOUDOS_TOKEN_HEADER, WebUtils.request().getHeader(Constant.Cloudos.CLOUDOS_TOKEN_HEADER));
        log.info("get X-Auth-Token success: {}", WebUtils.request().getHeader(Constant.Cloudos.CLOUDOS_TOKEN_HEADER));
        return headers;
    }

    public HttpHeaders tokenHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        //添加通过kong网关所必须的token
        headers.add(Constant.Cloudos.CLOUDOS_TOKEN_HEADER, token);

        return headers;
    }


    /**
     * 根据basePath和请求参数构建请求path
     *
     * @param basePath
     * @param params
     * @return
     */
    public String buildParameteredPath(String basePath, Map<String, Object> params) {
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString(basePath);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (Objects.nonNull(param.getValue())) {
                uriComponentsBuilder.queryParam(param.getKey(), param.getValue());
            }
        }
        return uriComponentsBuilder.build().toUriString();
    }

    private String accessUrl(String uri) {
        if (null != uri && uri.startsWith("/")) {
            uri = uri.substring(1);
        }

        StringBuilder sb = new StringBuilder()
                .append("http://")
                .append(gatewayServer)
                .append(":")
                .append(gatewayPort)
                .append("/")
                .append(uri);


        log.info("get cloudos url success: {}", sb);
        return sb.toString();
    }

}
