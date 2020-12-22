package com.h3c.vdi.viewscreen.security.handler;

import com.h3c.vdi.viewscreen.security.basic.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date 2020/7/28 14:07
 * @Created by lgw2845
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    private static final String USER_NON = "用户名不存在";
    private static final String PWD_ERROR = "密码不正确";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (userDetails == null) throw new UsernameNotFoundException(USER_NON);

        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userDetails.getPassword()))
            throw new BadCredentialsException(PWD_ERROR);

//        if (SM4Utils.webDecryptText(password).equals(SM4Utils.webDecryptText(userDetails.getPassword())))
//            throw new BadCredentialsException("密码不正确");


//        if (!password.equals(userDetails.getPassword())) throw new BadCredentialsException("密码不正确");

        // 进行登录
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
