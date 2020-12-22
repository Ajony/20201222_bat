package com.h3c.vdi.viewscreen.api.model.request.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/11/5 20:47
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class RequestMac {

    @ApiModelProperty(value = "mac地址")
    @NotBlank(message = "nicList不能为空")
    private String nicList;

    @ApiModelProperty(value = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

}
