package com.h3c.vdi.viewscreen.service.auth;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.api.model.request.auth.RequestUser;
import com.h3c.vdi.viewscreen.api.model.response.auth.ResponseToken;
import com.h3c.vdi.viewscreen.api.model.response.globalconfig.ResponseGlobalConfig;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.GlobalConfigDao;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dao.UserDao;
import com.h3c.vdi.viewscreen.dto.LoginInfo;
import com.h3c.vdi.viewscreen.entity.GlobalConfig;
import com.h3c.vdi.viewscreen.entity.Role;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.entity.User;
import com.h3c.vdi.viewscreen.security.basic.*;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import com.h3c.vdi.viewscreen.utils.jwt.TimeConstant;
import com.h3c.vdi.viewscreen.utils.sm4.SM4Utils;
import io.jsonwebtoken.*;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date 2020/7/21 11:37
 * @Created by lgw2845
 */
@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Resource
    UserDao userDao;

    @Resource
    GlobalConfigDao globalConfigDao;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Override
    public ApiResponse<ResponseToken> wsLogin(String uuid, String name, String password, String encryptStr) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid,Constant.LogicDelete.LOGIC_DELETE_N);
        if(Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Login.LOGIN, encryptStr, name, password);
        ResponseEntity<LoginInfo> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().postForEntity(url, null, LoginInfo.class);
        LoginInfo loginInfo = result.getBody();
        // 登录成功,产生token，并设置过期时间
        if (StringUtils.isBlank(loginInfo.getLoginFailMessage())) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", name);
            map.put("password", password);
            map.put("ip", ip);
            String token = jwtUtil.createToken(map);
            ResponseToken returnValue = ResponseToken.builder()
                    .token(token)
                    .expTime(jwtUtil.getExpirationDateFromToken(token))
                    .build();
            return ApiResponse.buildSuccess(returnValue);
        }
        return ApiResponse.buildError(ApiErrorEnum.E50104, "");
    }

    //登录
    @Override
    public ApiResponse<ResponseUserDetails> login(RequestJwtUser requestJwtUser) {

        ApiResponse<ResponseUserDetails> returnValue = ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_FAILURE);

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestJwtUser.getUsername());
        if (SM4Utils.webDecryptText(requestJwtUser.getPassword()).equals(SM4Utils.webDecryptText(userDetails.getPassword()))) {
//          if (requestJwtUser.getPassword().equals(userDetails.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestJwtUser.getUsername(), requestJwtUser.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = jwtUtil.generateToken(userDetails);
            User user = userDao.findByUsernameAndLogicDelete(requestJwtUser.getUsername(), Constant.LogicDelete.LOGIC_DELETE_N);
            user.setPassword(null);
            ResponseUser responseUser = new ResponseUser();
            BeanUtils.copyProperties(user, responseUser);
            responseUser.setRoles(user.getRoles().stream().map(this::convertRoles).collect(Collectors.toList()));
            ResponseUserDetails.ResponseUserDetailsBuilder data = ResponseUserDetails.builder()
                    .token(token)
                    .expTime(jwtUtil.getExpirationDateFromToken(token))
                    .user(responseUser);

//            是否首次登录逻辑
            List<GlobalConfig> globalConfigs = globalConfigDao.findAll();
            if (CollectionUtils.isEmpty(globalConfigs)) {
                data.isInitConf(0);  //未初始化
            } else {
                data.isInitConf(1);       //已初始化
                GlobalConfig globalConfig = globalConfigs.get(0);
                ResponseGlobalConfig responseGlobalConfig = new ResponseGlobalConfig();
                BeanUtils.copyProperties(globalConfig, responseGlobalConfig);
                data.globalConfig(responseGlobalConfig);
            }

            returnValue = ApiResponse.buildSuccess(data.build());
        }
        return returnValue;
    }


    //修改密码
    @Override
    public ApiResponse<Boolean> updatePwd(RequestUser requestUser) {

        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_UPD_PWD_FAILURE);

        //校验新密码组成规则
        if (StringUtils.isNotBlank(requestUser.getNewPwd())) {
            String str = validateNewPwd(SM4Utils.webDecryptText(requestUser.getNewPwd()));
            if (Objects.nonNull(str)) {
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), str);
            }
        }


        if (StringUtils.isNotBlank(requestUser.getUsername())) {
            User user = userDao.findByUsernameAndLogicDelete(requestUser.getUsername(), Constant.LogicDelete.LOGIC_DELETE_N);
            if (Objects.nonNull(user)) {

                //todo 更新密码 description暂时保留明文密码
                if (StringUtils.isNotBlank(requestUser.getOldPwd()) && StringUtils.isNotBlank(requestUser.getNewPwd())) {

//                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//                    if (encoder.matches(requestUser.getOldPwd(), user.getPassword())) {
                    if (SM4Utils.webDecryptText(requestUser.getOldPwd()).equals(SM4Utils.webDecryptText(user.getPassword()))) {

                        if (SM4Utils.webDecryptText(requestUser.getNewPwd()).equals(SM4Utils.webDecryptText(user.getPassword()))) {
                            return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_NEW_PWD_NON_OLD_PWD_ALIKE);
                        }
//                        user.setDescription(SM4Utils.webDecryptText(requestUser.getNewPwd()));
                        user.setPassword(requestUser.getNewPwd());
                        user.setModifiedDate(LocalDateTime.now());
                        userDao.save(user);
                        returnValue = ApiResponse.buildSuccess(true);
                    } else {
                        returnValue = ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_OLD_PWD_FAILURE);
                    }
                } else {
                    returnValue = ApiResponse.buildSuccess(true);
                }
            } else {
                returnValue = ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_USER_NON);
            }

        }
        return returnValue;
    }

    //校验密码组成规则
    private String validateNewPwd(String password) {

        //必须包含字母 数字 及特殊符号
        String regex = "^(?=.*[a-zA-Z])(?=.*[1-9])(?=.*[\\W]).{8,32}$";

        //以任意字符结尾
        //String regex = "^(?=.*[a-zA-Z])(?=.*[1-9])(?=.*[\\W]).*$";

        if (password.matches("^.*[\\s]+.*$") || password.matches(".*\\s+$")) {
            return Constant.Message.ERROR_PWD_BLANK;
        }

        if (password.trim().length() < 8 || password.trim().length() > 32) {
            return Constant.Message.ERROR_PWD_LENGTH;
        }

        if (!password.matches(regex)) {
            return Constant.Message.ERROR_PWD_REGEX;
        }

//        if (password.matches("^(?:\\d+|[a-zA-Z]+|[!@#$%^&]+)$")) {
//            return "密码强度弱,密码必须是数字、字母、!@#,.;:+=-_符号的组合";
//        }
        return null;
    }

    //workspace rest client刷新token
    @Override
    public ApiResponse<ResponseToken> wsRefreshToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(JwtUtil.SECRET.getBytes())
                        //延期时间（单位秒）
                        .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                        .parseClaimsJws(token.replace(JwtUtil.TOKEN_PREFIX, ""));
                Map<String, Object> subject = claimsJws.getBody();
                Map<String, Object> newSubject = new HashMap<>();
                newSubject.put("username", subject.get("username"));
                newSubject.put("password", subject.get("password"));
                newSubject.put("ip", subject.get("ip"));
                String newToken = jwtUtil.createToken(newSubject);
                ResponseToken returnValue = ResponseToken.builder()
                        .token(newToken)
                        .expTime(jwtUtil.getExpirationDateFromToken(newToken))
                        .build();
                return ApiResponse.buildSuccess(returnValue);
            } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                log.error("Token parsing error! The token is invalid. reason: -->{token无效}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_INVALID);
            } catch (ExpiredJwtException e) {
                log.error("Token parsing error! The token is expired. reason: -->{token过期}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_EXPIRES);
            } catch (Exception e) {
                log.error("Unexpected token parsing error happens. reason: -->{token解析错误}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_DECODE);
            }
        }
        return ApiResponse.buildError(ApiErrorEnum.S00001, "not allow empty string");
    }

    //刷新token
    @Override
    public ApiResponse<ResponseToken> refreshToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(JwtUtil.SECRET)

                        //延期时间（单位秒）
                        .setAllowedClockSkewSeconds(TimeConstant.DEFAULT_LEEWAY_SECONDS)
                        .parseClaimsJws(token.replace(JwtUtil.TOKEN_PREFIX, ""));
                String subject = claimsJws.getBody().getSubject();
                String newToken = jwtUtil.generateToken(new Gson().fromJson(subject, UserDetailsImpl.class));

                ResponseToken returnValue = ResponseToken.builder()
                        .token(newToken)
                        .expTime(jwtUtil.getExpirationDateFromToken(newToken))
                        .build();
                return ApiResponse.buildSuccess(returnValue);
            } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                log.error("Token parsing error! The token is invalid. reason: -->{token无效}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_INVALID);
            } catch (ExpiredJwtException e) {
                log.error("Token parsing error! The token is expired. reason: -->{token过期}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_EXPIRES);
            } catch (Exception e) {
                log.error("Unexpected token parsing error happens. reason: -->{token解析错误}");
                return ApiResponse.buildError(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_TOKEN_DECODE);
            }
        }
        return ApiResponse.buildError(ApiErrorEnum.S00001, "not allow empty string");
    }


    private ResponseRole convertRoles(Role role) {
        ResponseRole responseRole = new ResponseRole();
        BeanUtils.copyProperties(role, responseRole);
        return responseRole;
    }

}
