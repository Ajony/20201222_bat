//package com.h3c.vdi.viewscreen.security.handler;
//
//import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
//import com.h3c.vdi.viewscreen.api.result.ApiResponse;
//import com.h3c.vdi.viewscreen.utils.common.ResultUtil;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by x19765 on 2020/10/26.
// */
//public class WsAuthenticationEntryPointImpl implements AuthenticationEntryPoint {
//
//    /**
//     * 用户未登录返回结果
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
//        response.setStatus(HttpServletResponse.SC_OK);
//        ResultUtil.responseJson(response, ApiResponse.buildError(ApiErrorEnum.E50103, ""));
//    }
//}
