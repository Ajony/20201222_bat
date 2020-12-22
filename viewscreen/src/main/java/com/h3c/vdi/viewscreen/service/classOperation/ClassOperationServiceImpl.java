package com.h3c.vdi.viewscreen.service.classOperation;

import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.StateResult;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dto.BeginClassReqDTO;
import com.h3c.vdi.viewscreen.dto.EndClassReqDTO;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created by x19765 on 2020/10/14.
 */
@Service
@Slf4j
public class ClassOperationServiceImpl implements ClassOperationService {

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Override
    public ApiResponse<Boolean> beginClassAction(String uuid, BeginClassReqDTO beginClassReqDTO) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if(Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if(!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.ClassOperation.BEGIN_CLASS;
        ResponseEntity<StateResult> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().postForEntity(url, beginClassReqDTO, StateResult.class);
        StateResult stateResult = result.getBody();
        if(!stateResult.isSuccess()) {
            return returnValue;
        }
        return ApiResponse.buildSuccess(true);
    }

    @Override
    public ApiResponse<Boolean> endClassAction(String uuid, EndClassReqDTO endClassReqDTO) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid,Constant.LogicDelete.LOGIC_DELETE_N);
        if(Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
//        String token = WebUtils.request().getHeader(Constant.WS_TOKEN_HEADER);
//        boolean authStatus = jwtUtil.validateToken(token, ip);
//        if(!authStatus) {
//            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
//        }
        boolean status = jwtUtil.authVerify(ip);
        if(!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.ClassOperation.END_CLASS;
        ResponseEntity<StateResult> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().postForEntity(url, endClassReqDTO, StateResult.class);
        StateResult stateResult = result.getBody();
        if(!stateResult.isSuccess()) {
            return returnValue;
        }
        return ApiResponse.buildSuccess(true);
    }



}
