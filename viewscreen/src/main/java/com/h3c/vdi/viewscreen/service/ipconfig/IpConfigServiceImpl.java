package com.h3c.vdi.viewscreen.service.ipconfig;

import com.h3c.vdi.viewscreen.api.model.request.ipconfig.RequestIpInfo;
import com.h3c.vdi.viewscreen.api.model.response.ipconfig.ResponseIpInfo;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date 2020/11/10 16:36
 * @Created by lgw2845
 */
@Service
@Log4j2
public class IpConfigServiceImpl implements IpConfigService {

    @Resource
    ShareAreaDao shareAreaDao;


    @Override
    public ApiResponse<ResponseIpInfo> ipInfo(String uuid) {

        ApiResponse<ResponseIpInfo> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, uuid);

        ShareArea data = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.nonNull(data)) {

            ResponseIpInfo.ResponseIpInfoBuilder builder = ResponseIpInfo.builder();
            String ipList = data.getIpList();
            if (StringUtils.isNotBlank(ipList)) {
                if (ipList.contains(",")) {
                    builder.ipList(Stream.of(ipList.split(",")).collect(Collectors.toList()));
                } else {
                    builder.ipList(Stream.of(ipList).collect(Collectors.toList()));
                }
            }
            builder.ip(data.getIp());
            returnValue = ApiResponse.buildSuccess(builder.build());
        }
        return returnValue;
    }


    @Override
    public ApiResponse<Boolean> updIp(RequestIpInfo requestIpInfo) {

        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00003, "");
        try {
            shareAreaDao.updIp(requestIpInfo.getIp(), Constant.Message.IP_CONFIG, requestIpInfo.getUuid());
            returnValue = ApiResponse.buildSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return returnValue;
    }
}
