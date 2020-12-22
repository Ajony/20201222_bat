package com.h3c.vdi.viewscreen.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by x19765 on 2020/10/23.
 */
@Data
@ApiModel(value = "虚拟机详情")
public class DesktopStatisticsDTO implements Serializable {
    private static final long serialVersionUID = -8444511379564409479L;

    private Integer domainShutOffNum;
    private Integer domainPausedNum;
    private Integer domainRunningNum;
    private Integer domainAbnormalNum;
    private Integer domainUnkownNum;
}
