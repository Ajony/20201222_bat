package com.h3c.vdi.viewscreen.service.auth;

import com.h3c.vdi.viewscreen.api.model.request.auth.RequestUser;
import com.h3c.vdi.viewscreen.api.model.response.auth.ResponseToken;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.security.basic.RequestJwtUser;
import com.h3c.vdi.viewscreen.security.basic.ResponseUserDetails;

/**
 * @Classname AuthService
 * @Date 2020/7/21 11:37
 * @Created by lgw2845
 */
public interface AuthService {
    ApiResponse<ResponseUserDetails> login(RequestJwtUser requestJwtUser);

    ApiResponse<Boolean> updatePwd(RequestUser requestUser);

    ApiResponse<ResponseToken> refreshToken(String token);

    ApiResponse<ResponseToken> wsRefreshToken(String token);

    ApiResponse<ResponseToken> wsLogin(String uuid, String loginName, String password, String encryptStr);

}

