package com.h3c.vdi.viewscreen.security.config;

import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.filter.TokenAuthFilter;
import com.h3c.vdi.viewscreen.security.handler.AuthAccessDeniedImpl;
import com.h3c.vdi.viewscreen.security.handler.AuthenticationEntryPointImpl;
import com.h3c.vdi.viewscreen.security.handler.AuthenticationProviderImpl;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Date 2020/7/20 16:00
 * @Created by lgw2845
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 自定义暂无权限处理器
     */
    @Resource
    private AuthAccessDeniedImpl authAccessDenied;

    /**
     * 自定义未登录的处理器
     */
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 自定义登录逻辑验证器
     */
    @Resource
    private AuthenticationProviderImpl authenticationProvider;

    /**
     * 因为这里我们实现了 UserDetailsService，Spring会自动在项目中查找它的实现类，
     * 这里注入的也自然是我们自定义的  UserDetailsServiceImpl类。
     */
    @Resource
    private UserDetailsService userDetailsService;

    //读取环境变量，区分云上云下，云上暂时不做鉴权
    @Value("${vdi.service.provider.viewscreen}")
    private String provider;


    /**
     * 配置自己实现的UserDetailsService,并设置密码的加密方式，加密方式不是必须的，这里是可以明文密码
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里可启用我们自己的登陆验证逻辑
//        auth.authenticationProvider(authenticationProvider);
//        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

        //实现自定义加密方式
//        auth.userDetailsService(this.userDetailsService).passwordEncoder(new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return SM4Utils.webEncryptText(String.valueOf(rawPassword));
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return SM4Utils.webDecryptText(rawPassword.toString()).equals(SM4Utils.webDecryptText(encodedPassword));
//            }
//        });
        super.configure(auth);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (provider.equals(Constant.EnvPath.CLOUD_ON)) {
            http.authorizeRequests()
                    .anyRequest().permitAll().and().csrf().disable().cors();
            log.info("云上环境所有接口不做认证 SUCCESS: {}", "All requests are allowed access");
        } else {
            http.authorizeRequests().antMatchers("/auth/**").permitAll()
                    .antMatchers("/globalConfig/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/file/download").permitAll()
                    .antMatchers("/report/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
                    .and().authorizeRequests()
                    .antMatchers("/doc.html").permitAll()
                    .antMatchers("/**/*.ico").permitAll()
                    .antMatchers("/swagger-resources/**").permitAll()
                    .antMatchers("/v2/**").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
                    //其他需要登录后才能访问
                    .anyRequest().authenticated()
                    .and()
                    //添加JWT过滤器
                    .addFilterBefore(new TokenAuthFilter(authenticationManager(), jwtUtil), BasicAuthenticationFilter.class)
                    // 配置未登录自定义处理类
//                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                    .exceptionHandling()
//                //配置未登录自定义处理类
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    // 取消跨站请求伪造防护
                    .csrf().disable()
                    // 开启跨域
                    .cors()
                    .and()
                    // 基于Token不需要session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.headers()
                    //禁用缓存
                    .cacheControl();
//                .and()
//                 开启允许iframe 嵌套
//                .frameOptions().disable();
        }
    }

}
