//package com.h3c.vdi.viewscreen.utils.jwt;
//
//import com.google.gson.Gson;
//import com.h3c.vdi.viewscreen.utils.common.JwtUtil;
//import io.jsonwebtoken.*;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.Field;
//import java.util.*;
//
///**
// * @Date 2020/7/17 16:00
// * @Created by lgw2845
// */
//@Slf4j
//public class TokenUtil {
//
//    private final String SECRET;
//    private final String tokenPrefix;
//    private final String tokenSuffix;
//
//    public TokenUtil(String secret) {
//        this.SECRET = secret;
//        tokenPrefix = null;
//        tokenSuffix = null;
//    }
//
//    public TokenUtil(String SECRET, String tokenPrefix, String tokenSuffix) {
//        this.SECRET = SECRET;
//        this.tokenPrefix = tokenPrefix;
//        this.tokenSuffix = tokenSuffix;
//    }
//
//    public TokenModel decodePayloadFromToken(String token)
//            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
//        Claims claims = decodeClaimsFromToken(token, 0);
//        return convertToSSOTokenModel(claims);
//    }
//
//    public TokenModel decodePayloadFromToken(String token, long skewSeconds)
//            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
//
//        Claims claims = decodeClaimsFromToken(token, skewSeconds);
//        return convertToSSOTokenModel(claims);
//    }
//
//    public TokenModel decodePayloadFromVdiToken(String token, long skewSeconds)
//            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
//
//        Claims claims = decodeClaimsFromToken(token, skewSeconds);
//        return convertToSSOTokenVdiModel(claims);
//    }
//
//    private TokenModel convertToSSOTokenModel(Claims claims) {
//        TokenModel ssoTokenModel = new TokenModel();
//        Field[] fields = TokenModel.class.getDeclaredFields();
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true);
//                field.set(ssoTokenModel, claims.get(field.getName(), field.getType()));
//            } catch (IllegalAccessException ignored) {
//            }
//        }
//        return ssoTokenModel;
//    }
//
//    private TokenModel convertToSSOTokenVdiModel(Claims claims) {
//        String subject = claims.getSubject();
//        TokenModel fromJson = new Gson().fromJson(subject, TokenModel.class);
//        fromJson.setExpTime(claims.getExpiration());
//        return fromJson;
//    }
//
//    public Claims decodeClaimsFromToken(String token, long skewSeconds) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
//        return Jwts.parser().setSigningKey(JwtUtil.SECRET).setAllowedClockSkewSeconds(skewSeconds).parseClaimsJws(token.replace(JwtUtil.TOKEN_HEAD, "")).getBody();
//    }
//
//
//    private String ridOfPrefixAndSuffix(String token) {
//        if (Objects.isNull(tokenPrefix) && Objects.isNull(tokenSuffix)) {
//            return token;
//        }
//        int head = 0;
//        int tail = token.length();
//        if (Objects.nonNull(tokenPrefix)) {
//            if (!token.startsWith(tokenPrefix)) {
//                throw new SignatureException("Not valid token prefix");
//            }
//            head = tokenPrefix.length();
//        }
//        if (Objects.nonNull(tokenSuffix)) {
//            if (!token.endsWith(tokenSuffix)) {
//                throw new SignatureException("Not valid token suffix");
//            }
//            tail = tail - tokenSuffix.length();
//        }
//        return token.substring(head, tail);
//
//    }
//
//    @Data
//    public static class TokenModel {
//        private Long userId;
//        private String username;
//        private String role;
//        private List<String> authorities;
//        private Date expTime;
//    }
//}
