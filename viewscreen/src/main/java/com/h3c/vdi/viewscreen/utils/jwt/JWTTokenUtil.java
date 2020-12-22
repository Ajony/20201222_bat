package com.h3c.vdi.viewscreen.utils.jwt;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.security.basic.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成Token
     */
    public static String createAccessToken(UserDetailsImpl user){
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(user.getId()+"")
                // 主题
                .setSubject(user.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("lgq")
                // 自定义属性 放入用户拥有权限
                .claim("authorities", new Gson().toJson(user.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_TIME))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtUtil.SECRET)
                .compact();
        return token;
    }
}