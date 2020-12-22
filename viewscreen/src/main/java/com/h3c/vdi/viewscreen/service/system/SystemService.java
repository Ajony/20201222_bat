/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SystemService
 * Author:   ykf8829
 * Date:     2020/6/24 14:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.system;

import com.h3c.vdi.viewscreen.api.model.response.college.HostDto;
import com.h3c.vdi.viewscreen.api.model.response.college.SystemDto;
import com.h3c.vdi.viewscreen.api.model.response.college.VmDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/24
 * @since 1.0.0
 */
public interface SystemService {
    ApiResponse<SystemDto> getSystem(String uuid);

    ApiResponse<List<HostDto>> hostTop5CpuAvailability(String uuid);

    ApiResponse<List<HostDto>> hostTop5MemoryAvailability(String uuid);

    ApiResponse<List<HostDto>> hostTop5StorageAvailability(String uuid);

    ApiResponse<List<VmDto>> vmTop5CpuAvailability(String uuid);

    ApiResponse<List<VmDto>> vmTop5MemoryAvailability(String uuid);

    ApiResponse<List<VmDto>> vmTop5StorageAvailability(String uuid);
}
