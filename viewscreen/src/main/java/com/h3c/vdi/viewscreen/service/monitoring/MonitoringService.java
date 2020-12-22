package com.h3c.vdi.viewscreen.service.monitoring;

import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseMonitoring;
import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponsePageCollegeDynamic;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;

/**
 * @author lgq
 * @since 2020/6/19 16:06
 */
public interface MonitoringService {
    ApiResponse<ResponseMonitoring> query();

    ApiResponse<ApiPage<ResponsePageCollegeDynamic>> queryPage(Integer current, Integer size);

    ApiResponse<ResponseMonitoring> query(String code,Integer type);
}
