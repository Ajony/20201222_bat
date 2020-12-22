package com.h3c.vdi.viewscreen.utils.common.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
public class WebUtils {

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest request() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static HttpServletResponse response() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .map(ServletRequestAttributes::getResponse).orElse(null);
    }

    public  static Optional<Object> getRequestAttributeValue(String attributeKey) {
        return Optional.ofNullable(request()).map(request -> request.getAttribute(attributeKey));
    }



    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession session() {
        return request().getSession();
    }

}
