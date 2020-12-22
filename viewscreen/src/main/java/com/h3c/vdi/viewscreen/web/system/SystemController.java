/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SystemController
 * Author:   ykf8829
 * Date:     2020/6/24 14:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.system;

import com.h3c.vdi.viewscreen.api.model.response.college.HostDto;
import com.h3c.vdi.viewscreen.api.model.response.college.StorageDto;
import com.h3c.vdi.viewscreen.api.model.response.college.SystemDto;
import com.h3c.vdi.viewscreen.api.model.response.college.VmDto;
import com.h3c.vdi.viewscreen.api.model.response.storage.ResponseData;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.service.system.StorageService;
import com.h3c.vdi.viewscreen.service.system.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system")
@Slf4j
public class SystemController {

    @Resource
    private SystemService systemService;

    @Resource
    private StorageService storageService;

    @GetMapping("/info")
    public ApiResponse<SystemDto> getSystemInfo(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.getSystem(uuid);
    }

    @GetMapping("hostTop5CpuAvailability")
    public ApiResponse<List<HostDto>> getHostTop5CpuAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.hostTop5CpuAvailability(uuid);
    }

    @GetMapping("hostTop5MemoryAvailability")
    public ApiResponse<List<HostDto>> getHostTop5MemoryAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.hostTop5MemoryAvailability(uuid);
    }

    @GetMapping("hostTop5StorageAvailability")
    public ApiResponse<List<HostDto>> getHostTop5StorageAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.hostTop5StorageAvailability(uuid);
    }

    @GetMapping("vmTop5CpuAvailability")
    public ApiResponse<List<VmDto>> getVmTop5CpuAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.vmTop5CpuAvailability(uuid);
    }

    @GetMapping("vmTop5MemoryAvailability")
    public ApiResponse<List<VmDto>> getVmTop5MemoryAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.vmTop5MemoryAvailability(uuid);
    }

    @GetMapping("vmTop5StorageAvailability")
    public ApiResponse<List<VmDto>> getVmTop5StorageAvailability(@RequestParam(value = "uuid",required = false) String uuid){
        return systemService.vmTop5StorageAvailability(uuid);
    }

    @GetMapping("/storage")
    public ApiResponse<List<StorageDto>> storageIo(@RequestParam(value = "uuid",required = false) String uuid) {
        return storageService.storageList(uuid);
    }

    @GetMapping("/ioPs")
    public ApiResponse<ResponseData> ioPs(@RequestParam(value = "uuid",required = false) String uuid) {
        return storageService.ioPs(uuid);
    }


    @GetMapping("/io")
    public ApiResponse<ResponseData> io(@RequestParam(value = "uuid",required = false) String uuid) {
        return storageService.io(uuid);
    }
}
