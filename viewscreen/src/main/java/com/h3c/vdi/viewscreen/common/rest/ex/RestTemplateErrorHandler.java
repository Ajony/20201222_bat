package com.h3c.vdi.viewscreen.common.rest.ex;

/**
 * Created by x19765 on 2020/10/19.
 */

import com.h3c.vdi.viewscreen.dto.rs.RestResult;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.Charset;


/**
 * 自定义的Spring Rest Template异常处理器
 */
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) {

        try {
            super.handleError(response);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.info(null, e);
//            throw new HttpRestException(e.getStatusCode(), e.getStatusText(),
//                    e.getResponseHeaders(), e.getResponseBodyAsByteArray(), getCharset(response));
        } catch (Exception e) {
            log.warn("Rest Error.", e);
//            throw new HttpRestException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private RestResult getRestResult(String responseBody) {
        if (StringUtils.isBlank(responseBody)) {
            log.warn("Response Body is empty.");
            return defaultFailRestResult();
        }
        RestResult restResult = new RestResult();
        restResult.setResult(RestResult.FAIL);
        restResult.setMessage(responseBody);
        return restResult;
    }

    private RestResult defaultFailRestResult() {
        RestResult failResult = new RestResult();
        failResult.setErrorCode(-1);
        failResult.setMessage("rest error response empty");
        return failResult;
    }

    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }
        return Charsets.UTF_8;
    }
}
