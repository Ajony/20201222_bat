/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeServiceImpl
 * Author:   ykf8829
 * Date:     2020/6/23 19:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.college;

import com.h3c.vdi.viewscreen.api.model.response.college.CollegeInfoDto;
import com.h3c.vdi.viewscreen.api.model.response.college.TerminalDto;
import com.h3c.vdi.viewscreen.api.model.response.college.TerminalUseTimeDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.StateResult;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.CollegeInfoDao;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dao.TerminalHistoryDao;
import com.h3c.vdi.viewscreen.dto.VdiDeviceDTO;
import com.h3c.vdi.viewscreen.entity.CollegeInfo;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.entity.TerminalHistory;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/23
 * @since 1.0.0
 */
@Service
@Slf4j
public class CollegeServiceImpl implements CollegeService {

    @Resource
    private CollegeInfoDao collegeInfoDao;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private TerminalHistoryDao terminalHistoryDao;

    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Override
    public ApiResponse<CollegeInfoDto> collegeInfo(String uuid) {
        ApiResponse<CollegeInfoDto> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
        CollegeInfo collegeInfo = collegeInfoDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (collegeInfo != null) {
            CollegeInfoDto collegeInfoDto = new CollegeInfoDto();
            collegeInfoDto.setSystemHealthLevel(collegeInfo.getSyestemlevel());
            collegeInfoDto.setClassroomSum(collegeInfo.getClassroomSum());
            collegeInfoDto.setCourseSum(collegeInfo.getCourseSum());
            collegeInfoDto.setUserSum(collegeInfo.getUserSum());
            //todo  目前只做LS业务展示    WS待确定
//            collegeInfoDto.setDesktopRunningSum(collegeInfo.getDesktopRunningSum());
//            collegeInfoDto.setDesktopPauseSum(collegeInfo.getDesktopPauseSum());
//            collegeInfoDto.setDesktopCloseSum(collegeInfo.getDesktopCloseSum());
//            collegeInfoDto.setDesktopErrorSum(collegeInfo.getDesktopErrorSum());
//            collegeInfoDto.setDesktopUnknowSum(collegeInfo.getDesktopUnknowSum());
            collegeInfoDto.setDesktopRunningLsSum(collegeInfo.getDesktopRunningLsSum());
            collegeInfoDto.setDesktopPauseLsSum(collegeInfo.getDesktopPauseLsSum());
            collegeInfoDto.setDesktopCloseLsSum(collegeInfo.getDesktopCloseLsSum());
            collegeInfoDto.setDesktopErrorLsSum(collegeInfo.getDesktopErrorLsSum());
            collegeInfoDto.setDesktopUnknowLsSum(collegeInfo.getDesktopUnknowLsSum());
            returnValue = ApiResponse.buildSuccess(collegeInfoDto);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<TerminalUseTimeDto>> terminalUseTimeDtoList(String uuid) {
        ApiResponse<List<TerminalUseTimeDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
//        LocalDate now = LocalDate.now();
//        String endTime = now.format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
//        String beginTime = now.minusDays(9).format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
//        List<CollegeInfo> collegeInfos = collegeInfoDao.queryByUuidAndAddDate(uuid, endTime, beginTime);
//        if (!collegeInfos.isEmpty()) {
//            List<TerminalUseTimeDto> terminalUseTimeDtoList = new LinkedList<>();
//            for (CollegeInfo collegeInfo : collegeInfos) {
//                long onlineRate = 0L, total = collegeInfo.getTerminalTotal();
//                if (total != 0L) {
//                    onlineRate = collegeInfo.getTerminalOnlineSum() * 100 / total;
//                }
//                String date = collegeInfo.getAddDate().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_MONTH_DAY));
//                TerminalUseTimeDto terminalUseTimeDto = new TerminalUseTimeDto(date, collegeInfo.getTerminalUsetime(), onlineRate);
//                terminalUseTimeDtoList.add(terminalUseTimeDto);
//            }
//            returnValue = ApiResponse.buildSuccess(terminalUseTimeDtoList);
//        }
        List<TerminalHistory> list = terminalHistoryDao.findByUuidAndLogicDeleteOrderByAddDateAsc(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (CollectionUtils.isNotEmpty(list)) {
            List<TerminalUseTimeDto> response = list.stream().map(this::convertTerminalHistoryDto).collect(Collectors.toList());
            returnValue = ApiResponse.buildSuccess(response);
        }
        return returnValue;
    }

    private TerminalUseTimeDto convertTerminalHistoryDto(TerminalHistory item) {

        TerminalUseTimeDto terminalDto = new TerminalUseTimeDto();

        terminalDto.setUseTime(item.getTerminalUseTime().toString());
        terminalDto.setDateOf(item.getAddDate());
        if (item.getTerminalTotal() == 0 || item.getTerminalOnlineSum() == 0) {
            terminalDto.setOnlineRate("0");
        } else {
//            BigDecimal onlineSum = new BigDecimal(item.getTerminalOnlineSum());
//            BigDecimal total = new BigDecimal(item.getTerminalTotal());
//            BigDecimal divide = onlineSum.divide(total, 2, BigDecimal.ROUND_HALF_UP);
//            long var = divide.multiply(new BigDecimal(100)).longValue();
            //终端在线率 = 终端在线数 / 终端总数 * 100
            Long onlineSum = item.getTerminalOnlineSum();
            Long total = item.getTerminalTotal();
            String format = String.format("%.2f", ((onlineSum.doubleValue() / total.doubleValue()) * 100));
//          String format = String.format("%.2f", ((onlineSum.doubleValue() / total.doubleValue()) * 100)) + "%";
            terminalDto.setOnlineRate(format);
        }
        return terminalDto;
    }


    @Override
    public ApiResponse<TerminalDto> terminalInfo(String uuid) {
        ApiResponse<TerminalDto> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
        CollegeInfo collegeInfo = collegeInfoDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (collegeInfo != null) {
            TerminalDto terminalDto = new TerminalDto();
            terminalDto.setTerminalTotal(collegeInfo.getTerminalTotal());
            terminalDto.setTerminalIdvSum(collegeInfo.getTerminalIdvSum());
            terminalDto.setTerminalVdiSum(collegeInfo.getTerminalVdiSum());
            terminalDto.setTerminalVoiSum(collegeInfo.getTerminalVoiSum());
            terminalDto.setTerminalOfflineSum(collegeInfo.getTerminalOfflineSum());
            terminalDto.setTerminalOnlineSum(collegeInfo.getTerminalOnlineSum());
            returnValue = ApiResponse.buildSuccess(terminalDto);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<String> getUuid(String college) {
        ApiResponse<String> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String uuidByCollegeName = shareAreaDao.findUuidByCollegeName(college);
        if (null != uuidByCollegeName) {
            returnValue = ApiResponse.buildSuccess(uuidByCollegeName);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<VdiDeviceDTO>> queryTerminalsByClassroom(String uuid, Long classroomId) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        ApiResponse<List<VdiDeviceDTO>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + String.format(WsUriConstants.Terminal.QUERY_TERMINAL_LIST, classroomId);
        ResponseEntity<List<VdiDeviceDTO>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<VdiDeviceDTO>>() {
        });
        List<VdiDeviceDTO> vdiDeviceDTOList = result.getBody();
        if (CollectionUtils.isNotEmpty(vdiDeviceDTOList)) {
            returnValue = ApiResponse.buildSuccess(vdiDeviceDTOList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<Boolean> wakeUpTerminal(String uuid, List<Integer> ids) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
//        String token = WebUtils.request().getHeader(Constant.WS_TOKEN_HEADER);
//        boolean authStatus = jwtUtil.validateToken(token, ip);
//        if (!authStatus) {
//            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
//        }
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }

        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Terminal.TERMINAL_WAKE_UP;
        workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, ids, StateResult.class);
        return ApiResponse.buildSuccess(true);
    }

    @Override
    public ApiResponse<Boolean> shutDownTerminal(String uuid, List<Integer> ids) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
//        String token = WebUtils.request().getHeader(Constant.WS_TOKEN_HEADER);
//        boolean authStatus = jwtUtil.validateToken(token, ip);
//        if (!authStatus) {
//            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
//        }
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Terminal.TERMINAL_SHUT_DOWN;
        workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, ids, StateResult.class);
        return ApiResponse.buildSuccess(true);
    }

    @Override
    public ApiResponse<Boolean> restartTerminal(String uuid, List<Integer> ids) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if (!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Terminal.TERMINAL_RESTART;
        workspaceRestClient.workspaceRestClient(ip).getRestTemplate().put(url, ids, StateResult.class);
        return ApiResponse.buildSuccess(true);
    }
}
