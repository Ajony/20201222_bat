/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: StorageDto
 * Author:   ykf8829
 * Date:     2020/6/28 10:56
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
 * @create 2020/6/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class StorageDto {
    @ApiModelProperty(value = "io吞吐量")
    private Integer mbps;

    @ApiModelProperty(value = "IOPS百分数")
    private Integer percent;

    @ApiModelProperty(value = "时间")
    private String time;
}
