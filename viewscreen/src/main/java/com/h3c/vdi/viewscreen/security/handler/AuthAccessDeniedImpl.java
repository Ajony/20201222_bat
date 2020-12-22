package com.h3c.vdi.viewscreen.security.handler;

import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.utils.common.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2020/7/28 11:45
 * @Created by lgw2845
 * 暂无权限处理类
 */
@Component
public class AuthAccessDeniedImpl implements AccessDeniedHandler {

    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ResultUtil.responseJson(response, ApiResponse.buildError(ApiErrorEnum.S40003, ""));
    }
}
