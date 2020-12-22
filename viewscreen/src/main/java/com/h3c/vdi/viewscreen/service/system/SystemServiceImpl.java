/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SystemServiceImpl
 * Author:   ykf8829
 * Date:     2020/6/24 14:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.system;

import com.h3c.vdi.viewscreen.api.model.response.college.HostDto;
import com.h3c.vdi.viewscreen.api.model.response.college.SystemDto;
import com.h3c.vdi.viewscreen.api.model.response.college.VmDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.CollegeInfoDao;
import com.h3c.vdi.viewscreen.dao.HostDao;
import com.h3c.vdi.viewscreen.dao.VmDao;
import com.h3c.vdi.viewscreen.entity.CollegeInfo;
import com.h3c.vdi.viewscreen.entity.Host;
import com.h3c.vdi.viewscreen.entity.Vm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/24
 * @since 1.0.0
 */
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Resource
    private HostDao hostDao;

    @Resource
    private VmDao vmDao;

    @Resource
    private CollegeInfoDao collegeInfoDao;

    @Override
    public ApiResponse<SystemDto> getSystem(String uuid) {
        ApiResponse<SystemDto> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
        CollegeInfo collegeInfo = collegeInfoDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (null != collegeInfo) {
            SystemDto systemDto = new SystemDto();
            systemDto.setHostTotal(collegeInfo.getHostSum());
            systemDto.setHostNormal(collegeInfo.getHostNomalSum());
            systemDto.setHostMaintain(collegeInfo.getHostMaintainSum());
            systemDto.setHostClosed(collegeInfo.getHostClosedSum());
            systemDto.setVmTotal(collegeInfo.getVmTotal());
            systemDto.setVmRunning(collegeInfo.getVmRunningSum());
            systemDto.setVmError(collegeInfo.getVmErrorSum());
            systemDto.setVmRepair(collegeInfo.getVmRepairSum());
            returnValue = ApiResponse.buildSuccess(systemDto);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<HostDto>> hostTop5CpuAvailability(String uuid) {
        ApiResponse<List<HostDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Host> top5CpuAvailability = hostDao.findTop5CpuAvailability(uuid);
        if (!top5CpuAvailability.isEmpty()) {
            List<HostDto> hostDtoList = convertHostToHostDto(top5CpuAvailability);
            returnValue = ApiResponse.buildSuccess(hostDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<HostDto>> hostTop5MemoryAvailability(String uuid) {
        ApiResponse<List<HostDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Host> top5CpuAvailability = hostDao.findTop5MemoryAvailability(uuid);
        if (!top5CpuAvailability.isEmpty()) {
            List<HostDto> hostDtoList = convertHostToHostDto(top5CpuAvailability);
            returnValue = ApiResponse.buildSuccess(hostDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<HostDto>> hostTop5StorageAvailability(String uuid) {
        ApiResponse<List<HostDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Host> top5CpuAvailability = hostDao.findTop5StorageAvailability(uuid);
        if (!top5CpuAvailability.isEmpty()) {
            List<HostDto> hostDtoList = convertHostToHostDto(top5CpuAvailability);
            returnValue = ApiResponse.buildSuccess(hostDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<VmDto>> vmTop5CpuAvailability(String uuid) {
        ApiResponse<List<VmDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Vm> top5CpuAvailability = vmDao.findTop5CpuAvailability(uuid);
        if (!top5CpuAvailability.isEmpty()) {
            List<VmDto> vmDtoList = convertVmToVmDto(top5CpuAvailability);
            returnValue = ApiResponse.buildSuccess(vmDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<VmDto>> vmTop5MemoryAvailability(String uuid) {
        ApiResponse<List<VmDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Vm> top5MemoryAvailability = vmDao.findTop5MemoryAvailability(uuid);
        if (!top5MemoryAvailability.isEmpty()) {
            List<VmDto> vmDtoList = convertVmToVmDto(top5MemoryAvailability);
            returnValue = ApiResponse.buildSuccess(vmDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<VmDto>> vmTop5StorageAvailability(String uuid) {
        ApiResponse<List<VmDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Vm> Top5StorageAvailability = vmDao.findTop5StorageAvailability(uuid);
        if (!Top5StorageAvailability.isEmpty()) {
            List<VmDto> vmDtoList = convertVmToVmDto(Top5StorageAvailability);
            returnValue = ApiResponse.buildSuccess(vmDtoList);
        }
        return returnValue;
    }

    private List<HostDto> convertHostToHostDto(List<Host> hostList) {
        List<HostDto> hostDtoList = new ArrayList<>();
        for (Host host : hostList) {
            HostDto hostDto = new HostDto();
            hostDto.setHostName(host.getHostName());
            hostDto.setRate(host.getRate());
            hostDtoList.add(hostDto);
        }
        return hostDtoList;
    }

    private List<VmDto> convertVmToVmDto(List<Vm> vmList) {
        List<VmDto> vmDtoList = new ArrayList<>();
        for (Vm vm : vmList) {
            VmDto vmDto = new VmDto();
            vmDto.setVmName(vm.getVmName());
            vmDto.setRate(vm.getRate());
            vmDtoList.add(vmDto);
        }
        return vmDtoList;
    }
}
