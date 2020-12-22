package com.h3c.vdi.viewscreen.web.auth;

import com.h3c.vdi.viewscreen.api.model.request.auth.RequestUser;
import com.h3c.vdi.viewscreen.api.model.response.auth.ResponseToken;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.security.basic.RequestJwtUser;
import com.h3c.vdi.viewscreen.security.basic.ResponseUserDetails;
import com.h3c.vdi.viewscreen.service.auth.AuthService;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname AuthController
 * @Date 2020/7/20 13:50
 * @Created by lgw2845
 */
@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "auth-controller")
public class AuthController {

    @Resource
    AuthService authService;


    @ApiOperation(value = "登录", notes = "登录认证")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "body", dataType = "RequestJwtUser", name = "requestJwtUser", value = "请求参数", required = true)
    )
    @PostMapping("/login")
    public ApiResponse<ResponseUserDetails> login(@RequestBody @Validated RequestJwtUser requestJwtUser) {
        return authService.login(requestJwtUser);
    }


    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "body", dataType = "RequestUser", name = "requestUser", value = "请求参数", required = true)
    )
    @PostMapping("/updatePwd")
    public ApiResponse<Boolean> updatePwd(@RequestBody RequestUser requestUser) {
        return authService.updatePwd(requestUser);
    }


    @ApiOperation(value = "刷新token", notes = "刷新token")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "token", value = "请求参数", required = true)
    )
    @GetMapping("/refreshToken")
    public ApiResponse<ResponseToken> refreshToken(@RequestParam("token") String token) {
        return authService.refreshToken(token);
    }

    @GetMapping("/ws/refreshToken")
    public ApiResponse<ResponseToken> wsRefreshToken(@RequestParam("token") String token) {
        return authService.wsRefreshToken(token);
    }


    @ApiOperation(value = "退出", notes = "用户退出")
    @ApiImplicitParams({})
    @DeleteMapping("/out")
    public ApiResponse<Boolean> out(HttpServletResponse response) {
        response.setHeader(JwtUtil.TOKEN_HEADER, null);
        SecurityContextHolder.clearContext();
        return ApiResponse.buildSuccess(true);
    }


    /**
     * 登录workspace-server的接口
     */
    @PostMapping("/ws/login")
    public ApiResponse<ResponseToken> wslogin(@ApiParam(value = "局点uuid")@RequestParam(value = "uuid") String uuid,
                                                    @ApiParam(value = "登录名")@RequestParam(value = "name") String name,
                                                    @ApiParam(value = "密码")@RequestParam(value = "password") String password,
                                                    @ApiParam(value = "密码是否加密，设为false则密码可以输明文",defaultValue = "false")@RequestParam(value = "encrypt") String encryptStr) {
        return authService.wsLogin(uuid, name, password, encryptStr);
    }
}
