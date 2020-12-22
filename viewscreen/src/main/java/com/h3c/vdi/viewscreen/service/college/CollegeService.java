/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeService
 * Author:   ykf8829
 * Date:     2020/6/23 19:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.college;

import com.h3c.vdi.viewscreen.api.model.response.college.CollegeInfoDto;
import com.h3c.vdi.viewscreen.api.model.response.college.TerminalDto;
import com.h3c.vdi.viewscreen.api.model.response.college.TerminalUseTimeDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.VdiDeviceDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/23
 * @since 1.0.0
 */
public interface CollegeService {
    ApiResponse<CollegeInfoDto> collegeInfo(String uuid);

    ApiResponse<List<TerminalUseTimeDto>> terminalUseTimeDtoList(String uuid);

    ApiResponse<TerminalDto> terminalInfo(String uuid);

    ApiResponse<String> getUuid(String college);

    ApiResponse<List<VdiDeviceDTO>> queryTerminalsByClassroom(String uuid, Long classroomId);

    ApiResponse<Boolean> wakeUpTerminal(String uuid, List<Integer> ids);

    ApiResponse<Boolean> shutDownTerminal(String uuid, List<Integer> ids);

    ApiResponse<Boolean> restartTerminal(String uuid, List<Integer> ids);
}
