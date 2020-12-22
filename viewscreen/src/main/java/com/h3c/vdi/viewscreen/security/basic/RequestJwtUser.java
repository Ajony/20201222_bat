package com.h3c.vdi.viewscreen.security.basic;
/**
 * @Date 2020/7/17 16:00
 * @Created by lgw2845
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "")
public class RequestJwtUser implements Serializable {

    private static final long serialVersionUID = 6089544410175757451L;

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
