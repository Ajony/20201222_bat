/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ClassroomService
 * Author:   ykf8829
 * Date:     2020/5/28 18:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.classroom;

import com.h3c.vdi.viewscreen.api.model.response.college.ClassroomClassDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.ClassroomDTO;
import com.h3c.vdi.viewscreen.dto.DesktopStatisticsDTO;
import com.h3c.vdi.viewscreen.dto.DomainDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/28
 * @since 1.0.0
 */
public interface ClassroomService {
    ApiResponse<List<ClassroomClassDto>> classroomList(String uuid);

    ApiResponse<List<ClassroomDTO>> getClassroomInfo(String uuid);

    ApiResponse<ClassroomDTO> getClassroomInfoById(String uuid, Long id);

    ApiResponse<List<DomainDTO>> queryDomainsByClassroomId(String uuid, Long classroomId);

    ApiResponse<Boolean> shutDownDomain(String uuid, Long domainId);

    ApiResponse<Boolean> startDomain(String uuid, Long domainId);

    ApiResponse<Boolean> restartDomain(String uuid, Long domainId);

    ApiResponse<DesktopStatisticsDTO> queryDesktopStatusStatistics(String uuid, Long classroomId);

    ApiResponse<Boolean> ping(String ip);
    
}