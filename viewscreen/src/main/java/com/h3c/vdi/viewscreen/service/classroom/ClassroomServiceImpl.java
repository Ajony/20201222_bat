/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ClassroomServiceImpl
 * Author:   ykf8829
 * Date:     2020/5/28 18:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.classroom;

import com.h3c.vdi.viewscreen.api.model.response.college.ClassroomClassDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.StateResult;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.ClassroomDao;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dto.ClassroomDTO;
import com.h3c.vdi.viewscreen.dto.DesktopStatisticsDTO;
import com.h3c.vdi.viewscreen.dto.DomainDTO;
import com.h3c.vdi.viewscreen.entity.Classroom;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/28
 * @since 1.0.0
 */
@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {

    @Resource
    private ClassroomDao classroomDao;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Override
    public ApiResponse<List<ClassroomClassDto>> classroomList(String uuid) {
        ApiResponse<List<ClassroomClassDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");

        LocalDate now = LocalDate.now();
        String endTime = now.format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_YEAR_MONTH));
        String beginTime = now.minusMonths(10).format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_YEAR_MONTH));
        List<Classroom> classrooms = classroomDao.byUuidAndDate(uuid, endTime, beginTime);
        if (!classrooms.isEmpty()) {
            List<ClassroomClassDto> list = new LinkedList<>();
            for (Classroom classroom : classrooms) {
                ClassroomClassDto classroomClassDto = new ClassroomClassDto();
                classroomClassDto.setClassNumber(classroom.getClassCount());
                classroomClassDto.setDateTime(classroom.getDate());
                list.add(classroomClassDto);
            }
            returnValue = ApiResponse.buildSuccess(list);
        }
        return returnValue;
    }


    @Override
    public ApiResponse<List<ClassroomDTO>> getClassroomInfo(String uuid) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        ApiResponse<List<ClassroomDTO>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Classroom.QUERY_CLASSROOM_LIST;
            ResponseEntity<List<ClassroomDTO>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassroomDTO>>() {
            });
            List<ClassroomDTO> classroomDTOList = result.getBody();
            if (CollectionUtils.isNotEmpty(classroomDTOList)) {
                returnValue = ApiResponse.buildSuccess(classroomDTOList);
            }
            return returnValue;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public ApiResponse<ClassroomDTO> getClassroomInfoById(String uuid, Long classroomId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Classroom.QUERY_CLASSROOM_BY_ID, classroomId);
            ResponseEntity<ClassroomDTO> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, ClassroomDTO.class);
            ClassroomDTO classroomDTO = result.getBody();
            return ApiResponse.buildSuccess(classroomDTO);
        } catch (Exception e) {
            return ApiResponse.buildError(ApiErrorEnum.E00001, "");
        }
    }

    @Override
    public ApiResponse<List<DomainDTO>> queryDomainsByClassroomId(String uuid, Long classroomId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        ApiResponse<List<DomainDTO>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Domain.QUERY_DOMAIN_LIST, classroomId);
        ResponseEntity<List<DomainDTO>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DomainDTO>>() {
        });
        List<DomainDTO> domainDTOList = result.getBody();
        if (CollectionUtils.isNotEmpty(domainDTOList)) {
            returnValue = ApiResponse.buildSuccess(domainDTOList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<Boolean> shutDownDomain(String uuid, Long domainId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Domain.DOMAIN_SHUT_DOWN, domainId);
        workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, null, StateResult.class);
        return ApiResponse.buildSuccess(true);
    }

    @Override
    public ApiResponse<Boolean> startDomain(String uuid, Long domainId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Domain.DOMAIN_START, domainId);
            workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, null, StateResult.class);
            return ApiResponse.buildSuccess(true);
        } catch (Exception e) {
            return ApiResponse.buildError(ApiErrorEnum.E00001, "");
        }
    }

    @Override
    public ApiResponse<Boolean> restartDomain(String uuid, Long domainId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Domain.DOMAIN_RESTART, domainId);
            workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, null, StateResult.class);
            return ApiResponse.buildSuccess(true);
        } catch (Exception e) {
            return ApiResponse.buildError(ApiErrorEnum.E00001, "");
        }
    }

    @Override
    public ApiResponse<DesktopStatisticsDTO> queryDesktopStatusStatistics(String uuid, Long classroomId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        DesktopStatisticsDTO desktopStatisticsDTO = new DesktopStatisticsDTO();
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Domain.QUERY_DOMAIN_LIST, classroomId);
            ResponseEntity<List<DomainDTO>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DomainDTO>>() {
            });
            List<DomainDTO> domainDTOS = result.getBody();
            List<DomainDTO> domainDTOSRunning = domainDTOS.stream().filter(domainDTO -> domainDTO.getVmStatus().equals("running")).collect(Collectors.toList());
            List<DomainDTO> domainDTOSShutOff = domainDTOS.stream().filter(domainDTO -> domainDTO.getVmStatus().equals("shutOff")).collect(Collectors.toList());
            List<DomainDTO> domainDTOSPaused = domainDTOS.stream().filter(domainDTO -> domainDTO.getVmStatus().equals("paused")).collect(Collectors.toList());
            List<DomainDTO> domainDTOSUnknow = domainDTOS.stream().filter(domainDTO -> domainDTO.getVmStatus().equals("unknown")).collect(Collectors.toList());
            List<DomainDTO> domainDTOSAbnormal = domainDTOS.stream().filter(domainDTO -> domainDTO.getVmStatus().equals("abnormal")).collect(Collectors.toList());
            desktopStatisticsDTO.setDomainAbnormalNum(domainDTOSAbnormal.size());
            desktopStatisticsDTO.setDomainShutOffNum(domainDTOSShutOff.size());
            desktopStatisticsDTO.setDomainRunningNum(domainDTOSRunning.size());
            desktopStatisticsDTO.setDomainPausedNum(domainDTOSPaused.size());
            desktopStatisticsDTO.setDomainUnkownNum(domainDTOSUnknow.size());
            return ApiResponse.buildSuccess(desktopStatisticsDTO);
        } catch (Exception e) {
            return ApiResponse.buildError(ApiErrorEnum.E00001, "");
        }
    }

    @Override
    public ApiResponse<Boolean> ping(String ip) {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        try {
            String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Domain.BASE + "/ping";
            ResponseEntity<String> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            String var = result.getBody();
            if (StringUtils.isNotBlank(var)) {
                if (var.equals("pong")) returnValue = ApiResponse.buildSuccess(true);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return returnValue;
    }
}
