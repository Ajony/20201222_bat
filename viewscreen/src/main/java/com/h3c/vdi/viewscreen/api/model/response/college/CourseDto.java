/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CourseDto
 * Author:   ykf8829
 * Date:     2020/6/16 14:29
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
 * @create 2020/6/16
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class CourseDto {
    @ApiModelProperty(value = "课程名称")
    private String courseName;
    @ApiModelProperty(value = "上课次数")
    private Integer classNumber;

}
