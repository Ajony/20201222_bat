package com.h3c.vdi.viewscreen.web.monitoring;

import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseMonitoring;
import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponsePageCollegeDynamic;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;
import com.h3c.vdi.viewscreen.service.monitoring.MonitoringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lgq
 * @since 2020/6/19 16:03
 */
@RestController
@RequestMapping("/monitoring")
@Log4j2
@Api(tags = "monitoring-controller")
@Validated
public class MonitoringController {

    @Resource
    private MonitoringService monitoringService;


    @ApiOperation(value = "获取全国数据", notes = "获取全国监控数据")
    @GetMapping("/nationwide/query")
//    @PreAuthorize("hasAnyRole('admin','operator')")
//    @PreAuthorize("hasRole('operator')")
//    @Secured({"ROLE_operator","ROLE_admin"})
//    @Secured({"ROLE_admin"})
    public ApiResponse<ResponseMonitoring> query() {
        return monitoringService.query();
    }


    @ApiOperation(value = "获取分页数据(首页机构动态)", notes = "当前页和每页条数必填数字,分页查询(首页机构动态)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "current", required = true, defaultValue = "1", value = "起始页码", example = "1"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "size", required = true, defaultValue = "5", value = "每页条数", example = "5")
    })
    @GetMapping(value = "/nationwide/queryPage")
    public ApiResponse<ApiPage<ResponsePageCollegeDynamic>> queryPage(@RequestParam("current") @NotNull(message = "not null current") Integer current,
                                                                      @RequestParam("size") @NotNull(message = "not null size") Integer size) {
        return monitoringService.queryPage(current, size);
    }


    @ApiOperation(value = "获取地区数据", notes = "获取地区监控数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", required = true, value = "地区编码"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "type", required = true, value = "地区编码",example = "1")
    })
    @GetMapping("/area/query")
    public ApiResponse<ResponseMonitoring> query(@NotBlank(message = "not null code") @RequestParam(value = "code") String code,
                                                 @NotNull(message = "not null type") @RequestParam(value = "type") Integer type) {
        return monitoringService.query(code,type);
    }


    public static void main(String[] args) {
        log.info(SpringBootVersion.getVersion());
        log.info(SpringVersion.getVersion());
    }


}
