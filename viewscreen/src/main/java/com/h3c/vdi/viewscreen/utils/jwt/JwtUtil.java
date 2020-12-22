package com.h3c.vdi.viewscreen.utils.jwt;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.common.rest.WebUtils;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.exception.BusinessException;
import com.h3c.vdi.viewscreen.security.basic.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Date 2020/7/17 16:00
 * @Created by lgw2845
 */
@Component
@Log4j2
public class JwtUtil {
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String SECRET = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    //    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_HEADER = "X-Auth-Token";
    public static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;      //2hour
//    public static final long EXPIRE_TIME = 10 * 60 * 1000;        //10minute
    public static final long WS_EXPIRE_TIME = 5 * 60 * 1000;


    public String getUsernameFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            String subject = claims.getSubject();
            UserDetailsImpl userDetails = convertTokenToUserDetails(subject);
            return userDetails.getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromToken(String token, long skewSeconds) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .setAllowedClockSkewSeconds(skewSeconds)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String subject = claims.getSubject();
            UserDetailsImpl userDetails = convertTokenToUserDetails(subject);
            return userDetails.getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Jws<Claims> getJwsClaimsFromToken1(String token) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    //todo 设置延时过期时间
                    .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.error("Token parsing error! The token is invalid. reason: -->{token无效}", e);
        } catch (ExpiredJwtException e) {
            log.error("Token parsing error! The token is expired. reason: -->{token过期}", e);
        } catch (Exception e) {
            log.error("Unexpected token parsing error happens. reason: -->{token解析错误}", e);
        }
        return claims;
    }


    public Jws<Claims> getJwsClaimsFromToken(String token) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    //todo 设置延时过期时间
                    .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.error("Token parsing error! The token is invalid. reason: -->{token无效}", e);
        } catch (ExpiredJwtException e) {
            log.error("Token parsing error! The token is expired. reason: -->{token过期}", e);
        } catch (Exception e) {
            log.error("Unexpected token parsing error happens. reason: -->{token解析错误}", e);
        }
        return claims;
    }


    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    //todo 设置延时过期时间
                    .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + WS_EXPIRE_TIME);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return Objects.nonNull(expiration) && expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String createToken(Map<String, Object> claimMap) {
        long currentTimeMillis = System.currentTimeMillis();
        return TOKEN_PREFIX + Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))    // 设置签发时间
                .setExpiration(createExpirationDate())
                .addClaims(claimMap)
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public boolean validateToken(String token, String hostIp) {
        Map<String, Object> claims = null;
        try {
            claims = parseToken(token);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        //认证token
        if(claims.get("ip").equals(hostIp)) {
            return true;
        }
        return false;
    }

    public int verifyToken(String token) {
        try {
            Jwts.parser().setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 1;
        }

//        catch (ExpiredJwtException e) {
//            e.printStackTrace();
//            return 1;
//        } catch (UnsupportedJwtException e) {
//            e.printStackTrace();
//            return 2;
//        } catch (MalformedJwtException e) {
//            e.printStackTrace();
//            return 3;
//        } catch (SignatureException e) {
//            e.printStackTrace();
//            return 4;
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return 5;
//        }
    }

    /**
     * 生成安全密钥
     * @return
     */
    public SecretKey generateKey() {
        return new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(UserDetails userDetails) {
        String subject = new Gson().toJson(userDetails);
        return TOKEN_PREFIX + Jwts
                .builder()
                .setSubject(subject)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {

//        if (isTokenExpired(token)) return null;

        String subject;
        try {
            subject = Jwts.parser()
                    //todo 设置延时过期时间
                    .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            UserDetailsImpl userDetails = convertTokenToUserDetails(subject);
            subject = generateToken(userDetails);
        } catch (Exception e) {
            subject = null;
        }
        return subject;
    }

    private UserDetailsImpl convertTokenToUserDetails(String token) {
        return new Gson().fromJson(token, UserDetailsImpl.class);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Map<String, Object> parseToken(String token) {
        return Jwts.parser()  // 得到DefaultJwtParser
                .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                .setSigningKey(generateKey()) // 设置签名密钥
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    }


    public boolean authVerify(String ip) {
        String token = WebUtils.request().getHeader(Constant.WS_TOKEN_HEADER);
        if(StringUtils.isNotBlank(token) && !token.equals("null")) {
            //token延时
            Jws<Claims> claims = getJwsClaimsFromToken1(token);
            if(null != claims) {
                Date expiration = claims.getBody().getExpiration();
                if (expiration.before(new Date())) {
                    WebUtils.response().addHeader(Constant.WS_REFRESH_HEADER, Constant.REFRESH_TOKEN_TYPE);
                }
            }
        }
        //token认证
        boolean authStatus = validateToken(token, ip);
        if(!authStatus) {
            return false;
        }
        return true;
    }
}

