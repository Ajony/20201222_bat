package com.h3c.vdi.viewscreen.common.rest;


import com.h3c.vdi.viewscreen.dto.rs.RestType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;

/**
 * 继承Spring原来的RestTemplate，把泛型参数名字减短一点
 */
public class VdiRestTemplate extends RestTemplate {

    public VdiRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          RestType<T> responseType, Object... uriVariables) throws RestClientException {
        Type type = responseType.getType();
        RequestCallback requestCallback = httpEntityCallback(requestEntity, type);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
        return execute(url, method, requestCallback, responseExtractor, uriVariables);
    }

}
