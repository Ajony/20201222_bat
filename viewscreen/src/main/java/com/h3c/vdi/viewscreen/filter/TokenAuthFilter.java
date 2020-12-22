package com.h3c.vdi.viewscreen.filter;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.security.basic.UserDetailsImpl;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @Date 2020/7/17 16:00
 * @Created by lgw2845
 */
@Log4j2
public class TokenAuthFilter extends BasicAuthenticationFilter {

    public final String REFRESH_HEADER = "Refresh-Token";
    public final String REFRESH_TOKEN_TYPE = "Local";

    private final JwtUtil jwtUtil;


    public TokenAuthFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JwtUtil.TOKEN_HEADER);
        if (Objects.nonNull(token) && token.startsWith(JwtUtil.TOKEN_PREFIX)) {

            try {
                Claims claims = jwtUtil.getJwsClaimsFromToken(token).getBody();
                if (Objects.nonNull(claims.getSubject())) {
                    String subject = claims.getSubject();
                    UserDetailsImpl userDetails = convertUserDetails(subject);
                    if (Objects.nonNull(userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("token auth success: {}", request.getRequestURL());
                    }
                }

                Date expiration = claims.getExpiration();
                if (expiration.before(new Date())) {
                    response.addHeader(REFRESH_HEADER, REFRESH_TOKEN_TYPE);
                }
            } catch (Exception e) {
                log.error("Unexpected token parsing error happens. reason: -->{token解析错误}", e);
            }
        }

        chain.doFilter(request, response);
    }

    private UserDetailsImpl convertUserDetails(String token) {
        return new Gson().fromJson(token, UserDetailsImpl.class);
    }

}
