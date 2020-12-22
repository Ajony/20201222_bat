/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SystemDto
 * Author:   ykf8829
 * Date:     2020/6/24 14:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.api.model.response.college;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/24
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class SystemDto {
    private Long vmTotal;
    private Long vmRunning;
    private Long vmError;
    private Long vmRepair;
    private Long hostTotal;
    private Long hostNormal;
    private Long hostClosed;
    private Long hostMaintain;
}
