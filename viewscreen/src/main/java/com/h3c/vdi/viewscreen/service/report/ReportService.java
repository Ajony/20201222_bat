package com.h3c.vdi.viewscreen.service.report;

import com.h3c.vdi.viewscreen.api.model.request.report.RequestMac;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReport;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReportShareArea;
import com.h3c.vdi.viewscreen.api.model.response.report.ResponseReport;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;

import java.util.List;

/**
 * @author lgq
 * @since 2020/6/3 11:04
 */
@SuppressWarnings("rawtypes")
public interface ReportService {

    ApiResponse<ResponseReport> checkUuid(RequestMac requestMac);

    ApiResponse<Boolean> reportSwitch(String status, String uuid);

    ApiResponse<ResponseReport> reportData(List<RequestReport> requestReport, String uuid);

    ApiResponse<Boolean> reportShareArea(RequestReportShareArea requestReport);

}
