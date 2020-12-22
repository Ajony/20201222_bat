package com.h3c.vdi.viewscreen.service.auth;

import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseChinaArea;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.security.basic.ResponseUserDetails;

import java.util.List;

/**
 * @Classname GlobalConfigService
 * @Date 2020/7/20 15:19
 * @Created by lgw2845
 */
public interface GlobalConfigService {

    ApiResponse<Boolean> initConfig(String value, String text);

    ApiResponse<List<ResponseChinaArea>> queryArea(String addressCode);

    ApiResponse<Boolean> configDefaultPage(String value, String text);

    ApiResponse<ResponseUserDetails> queryInitCon();

}

