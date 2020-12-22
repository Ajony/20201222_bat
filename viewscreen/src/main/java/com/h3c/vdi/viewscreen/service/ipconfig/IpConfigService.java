package com.h3c.vdi.viewscreen.service.ipconfig;

import com.h3c.vdi.viewscreen.api.model.request.ipconfig.RequestIpInfo;
import com.h3c.vdi.viewscreen.api.model.response.ipconfig.ResponseIpInfo;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;

/**
 * @Date 2020/11/10 16:35
 * @Created by lgw2845
 */
public interface IpConfigService {

    ApiResponse<ResponseIpInfo> ipInfo(String uuid);

    ApiResponse<Boolean> updIp(RequestIpInfo requestIpInfo);
}
