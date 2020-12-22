package com.h3c.vdi.viewscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by x19765 on 2020/10/15.
 */
@Builder
@Data
@ApiModel("标题信息")
public class SubTitleDTO implements Serializable {
    private static final long serialVersionUID = -4349174582045803152L;

    @ApiModelProperty(value = "大标题")
    @NotBlank(message = "不能为空")
    private String logoTitle;

    @ApiModelProperty(value = "热点地图")
    @NotBlank(message = "不能为空")
    private String map;

    @ApiModelProperty(value = "局点动态")
    @NotBlank(message = "不能为空")
    private String siteDynamics;

    @ApiModelProperty(value = "省市局点信息")
    @NotBlank(message = "不能为空")
    private String siteInformation;

    @ApiModelProperty(value = "区域分布")
    @NotBlank(message = "不能为空")
    private String regionalDistribution;
}
