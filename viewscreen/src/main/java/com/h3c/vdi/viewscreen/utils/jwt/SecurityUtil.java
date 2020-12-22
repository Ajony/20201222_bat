package com.h3c.vdi.viewscreen.utils.jwt;

import com.h3c.vdi.viewscreen.security.basic.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 */
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static UserDetailsImpl getUserInfo(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }
    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getId();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}