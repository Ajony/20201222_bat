package com.h3c.vdi.viewscreen.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2020/7/23 17:33
 * @Created by lgw2845
 */
@Configuration
public class TomcatServletConfig {


    //todo 解决异常信息：java.lang.IllegalArgumentException: Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
    /*
     * 原因：
     *       SpringBoot 2.0.0 以上都采用内置tomcat8.0以上版本，
     *       而tomcat8.0以上版本遵从RFC规范添加了对Url的特殊字符的限制，
     *       url中只允许包含英文字母(a-zA-Z)、数字(0-9)、-_.~四个特殊字符以及保留字符( ! * ’ ( ) ; : @ & = + $ , / ? # [ ] ) (26*2+10+4+18=84)这84个字符,
     *       请求中出现了{}大括号或者[],所以tomcat报错。
     * */
    private final String NAME = "relaxedQueryChars";
    private final String VALUE = "|{}[]";

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty(NAME, VALUE);
            }
        });
        return factory;
    }


    //todo Tomcat 8 更换默认的 CookieProcessor 实现为 Rfc6265CookieProcessor ，之前的实现为 LegacyCookieProcessor 。前者是基于 RFC6265 ，而后者基于 RFC6265、RFC2109、RFC2616
    // Tomcat Cookie 处理配置 Bean
/*    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                //指定 CookieProcessor 为 org.apache.tomcat.util.http.LegacyCookieProcessor
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }*/



}
