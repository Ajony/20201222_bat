package com.h3c.vdi.viewscreen.api.model.request.bureaudot;

/**
 * @Date 2020/11/5 13:52
 * @Created by lgw2845
 */

import com.h3c.vdi.viewscreen.api.result.base.SuperRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class RequestPageBureauDot extends SuperRequestModel {

    @ApiModelProperty(value = "省市编码")
    private String provinceCode;

    @ApiModelProperty(value = "省市名称")
    private String provinceName;

    @ApiModelProperty(value = "市区编码")
    private String cityCode;

    @ApiModelProperty(value = "市区名称")
    private String cityName;

    @ApiModelProperty(value = "区县编码")
    private String countyCode;

    @ApiModelProperty(value = "区县名称")
    private String countyName;

    @ApiModelProperty(value = "学校名称")
    private String college;
}
