package com.h3c.vdi.viewscreen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date 2020/7/28 18:04
 * @Created by lgw2845
 */
@Configuration
public class GlobalCorsConfig {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*")
                        //是否发送Cookie信息
                        .allowCredentials(true).maxAge(3600)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("access-control-allow-headers",
                                "access-control-allow-methods",
                                "access-control-allow-origin",
                                "access-control-max-age",
                                "X-Frame-Options");
            }
        };


         /*   @Bean
            public CorsFilter corsFilter() {
                //1.添加CORS配置信息
                CorsConfiguration config = new CorsConfiguration();
                //放行哪些原始域
                config.addAllowedOrigin("*");
                //是否发送Cookie信息
                config.setAllowCredentials(true);
                //放行哪些原始域(请求方式)
                config.addAllowedMethod("*");
                //放行哪些原始域(头部信息)
                config.addAllowedHeader("*");
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                config.addExposedHeader("*");

                //2.添加映射路径
                UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
                configSource.registerCorsConfiguration("/**", config);

                //3.返回新的CorsFilter.
                return new CorsFilter(configSource);
            }*/


    }


}