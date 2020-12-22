package com.h3c.vdi.viewscreen.utils.common;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date 2020/10/17 10:49
 * @Created by lgw2845
 * 获取Ip地址
 */
@Log4j2
public class IpUtil {

    /**
     * 参数说明：
     * X-Real-IP	nginx 代理服务器IP
     * X-Forwarded-For	squid 代理服务器IP
     * Proxy-Client-IP	apache 代理服务器IP
     * WL-Proxy-Client-IP	weblogic 代理服务器IP
     * HTTP_CLIENT_IP	http 客户端IP
     * HTTP_X_FORWARDED_FOR	http 代理服务器IP
     */


    /**
     * 默认IP地址
     */
    public final static String ERROR_IP = "127.0.0.1";

    /**
     * IP地址正则表达式
     */
    public final static Pattern pattern = Pattern.compile(
            "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    /**
     * @param request
     * @return boolean
     * @title isValidIP
     * @description 根据请求验证IP地址
     */
    public static boolean isValidIP(HttpServletRequest request) {
        // 获取用户真实的IP地址
        String ip = getUserIP(request);
        // 验证IP地址是否符合规则
        return isValidIP(ip);
    }

    /**
     * @param request
     * @return String
     * @title getRemoteIp
     * @description 获取远程IP地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        // 过滤反向代理的ip
        String[] stemps = ip.split(",");
        if (stemps != null && stemps.length >= 1) {
            // 得到第一个IP，即客户端真实IP
            ip = stemps[0];
        }

        ip = ip.trim();
        if (ip.length() > 23) {
            ip = ip.substring(0, 23);
        }

        return ip;
    }

    /**
     * @param request
     * @return String
     * @title getUserIP
     * @description 获取用户真实的IP地址
     */
    public static String getUserIP(HttpServletRequest request) {
        // 优先取 X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        log.warn("var1_1：{}",ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
            log.warn("var1_2：{}",ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.warn("var1_3：{}",ip);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = ERROR_IP;
            }
        }
        if ("unknown".equalsIgnoreCase(ip)) {
            ip = ERROR_IP;
            return ip;
        }
        log.warn("var1_4：{}",ip);
        int index = ip.indexOf(',');
        if (index >= 0) {
            ip = ip.substring(0, index);
        }

        return ip;
    }

    /**
     * @param request
     * @return String
     * @title getLastIpSegment
     * @description 获取末尾IP地址段
     */
    public static String getLastIpSegment(HttpServletRequest request) {
        // 获取用户真实的IP地址
        String ip = getUserIP(request);
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * @param ip
     * @return boolean
     * @title isValidIP
     * @description 验证IP地址是否符合规则
     */
    public static boolean isValidIP(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * @return String
     * @title getLastServerIpSegment
     * @description 获取服务器的会后一个地址段
     */
    public static String getLastServerIpSegment() {
        String ip = getServerIP();
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * @return String
     * @title getServerIP
     * @description 获取服务器IP地址
     */
    public static String getServerIP() {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ERROR_IP;
    }

    /**
     * @param request
     * @return String
     * @title getIpAddress
     * @description <li>获取IP地址
     * <li><strong>注意：</strong>IP地址经过多次反向代理后会有多个IP值，
     * <li>其中第一个IP才是真实IP，所以不能通过request.getRemoteAddr()获取IP地址，
     * <li>如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * <li>X-Forwarded-For中第一个非unknown的有效IP才是用户真实IP地址。
     * </ul>
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        try {
            // 获取用户真实的地址
            String Xip = request.getHeader("X-Real-IP");
            log.warn("var1：{}",Xip);
            // 获取多次代理后的IP字符串值
            String XFor = request.getHeader("X-Forwarded-For");
            log.warn("var2：{}",XFor);
            if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
                // 多次反向代理后会有多个IP值，第一个用户真实的IP地址
                int index = XFor.indexOf(",");
                if (index >= 0) {
                    return XFor.substring(0, index);
                } else {
                    return XFor;
                }
            }
            ip = Xip;
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                log.warn("var3：{}",ip);
            }
            if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                log.warn("var4：{}",ip);
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                log.warn("var5：{}",ip);
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                log.warn("var6：{}",ip);
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                log.warn("var7：{}",ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
