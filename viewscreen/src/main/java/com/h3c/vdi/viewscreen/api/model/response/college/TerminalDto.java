/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TerminalDto
 * Author:   ykf8829
 * Date:     2020/6/30 16:36
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
 * @create 2020/6/30
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class TerminalDto {
    @ApiModelProperty(value = "终端总数")
    private Long terminalTotal;
    @ApiModelProperty(value = "终端在线数")
    private Long terminalOnlineSum;
    @ApiModelProperty(value = "终端离线数")
    private Long terminalOfflineSum;
    @ApiModelProperty(value = "终端vdi数")
    private Long terminalVdiSum;
    @ApiModelProperty(value = "终端idv数")
    private Long terminalIdvSum;
    @ApiModelProperty(value = "终端voi数")
    private Long terminalVoiSum;

}
