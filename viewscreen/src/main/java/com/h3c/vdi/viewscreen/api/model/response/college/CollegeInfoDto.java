/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeInfoDto
 * Author:   ykf8829
 * Date:     2020/5/29 9:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.api.model.response.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/29
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class CollegeInfoDto {

    @ApiModelProperty(value = "健康水平")
    private String systemHealthLevel;
    @ApiModelProperty(value = "教室数")
    private Long classroomSum;
    @ApiModelProperty(value = "课程数")
    private Long courseSum;
    @ApiModelProperty(value = "用户数")
    private Long userSum;
    @ApiModelProperty(value = "ws桌面运行数")
    private Long desktopRunningSum;
    @ApiModelProperty(value = "ws桌面暂停数")
    private Long desktopPauseSum;
    @ApiModelProperty(value = "ws桌面关闭数")
    private Long desktopCloseSum;
    @ApiModelProperty(value = "ws桌面异常数")
    private Long desktopErrorSum;
    @ApiModelProperty(value = "ws桌面未知数")
    private Long desktopUnknowSum;
    @ApiModelProperty(value = "ls桌面运行数")
    private Long desktopRunningLsSum;
    @ApiModelProperty(value = "ls桌面暂停数")
    private Long desktopPauseLsSum;
    @ApiModelProperty(value = "ls桌面关闭数")
    private Long desktopCloseLsSum;
    @ApiModelProperty(value = "ls桌面异常数")
    private Long desktopErrorLsSum;
    @ApiModelProperty(value = "ls桌面未知数")
    private Long desktopUnknowLsSum;
}
