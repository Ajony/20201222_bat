package com.h3c.vdi.viewscreen.api.model.request.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author lgq
 * @since 2020/6/17 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class RequestReportShareArea {


    @ApiModelProperty(value = "省市编码")
    @NotBlank(message = "省市编码不能为空")
    private String provinceCode;

    @ApiModelProperty(value = "省市名称")
    @NotBlank(message = "省市名称不能为空")
    private String provinceName;

    @ApiModelProperty(value = "市区编码")
    @NotBlank(message = "市区编码不能为空")
    private String cityCode;

    @ApiModelProperty(value = "市区名称")
    @NotBlank(message = "市区名称不能为空")
    private String cityName;

    @ApiModelProperty(value = "区县编码")
    @NotBlank(message = "区县编码不能为空")
    private String countyCode;

    @ApiModelProperty(value = "区县名称")
    @NotBlank(message = "区县名称不能为空")
    private String countyName;

    @ApiModelProperty(value = "学校名称")
    private String college;

    @ApiModelProperty(value = "UUID")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "IP列表")
    private String ipList;

    @ApiModelProperty(value = "scene")
    private String scene;

    @ApiModelProperty(value = "location")
    private String location;

}
