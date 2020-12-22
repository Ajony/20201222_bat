package com.h3c.vdi.viewscreen.security.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.api.model.response.globalconfig.ResponseGlobalConfig;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Date 2020/7/17 16:07
 * @Created by lgw2845
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户信息")
public class ResponseUserDetails {

    private String token;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date expTime;

    private ResponseUser user;

    private Integer isInitConf;

    private ResponseGlobalConfig globalConfig;

}
