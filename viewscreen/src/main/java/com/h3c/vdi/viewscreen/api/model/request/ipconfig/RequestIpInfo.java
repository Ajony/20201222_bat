package com.h3c.vdi.viewscreen.api.model.request.ipconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/11/10 19:46
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class RequestIpInfo {

    @ApiModelProperty(value = "ip")
    @NotBlank(message = "ip不能为空")
    private String ip;

    @ApiModelProperty(value = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;
}
