package com.h3c.vdi.viewscreen.common.rest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created by x19765 on 2020/10/17.
 */
public class WebUtils {


    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest request() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .map(servletRequestAttributes -> servletRequestAttributes.getRequest()).orElse(null);
    }

    public static HttpServletResponse response() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .map(servletRequestAttributes -> servletRequestAttributes.getResponse()).orElse(null);
    }

    public  static Optional<Object> getRequestAttributeValue(String attributeKey) {
        return Optional.ofNullable(request()).map(request -> request.getAttribute(attributeKey));
    }
}
