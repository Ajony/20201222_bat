package com.h3c.vdi.viewscreen.web.ipconfig;

import com.h3c.vdi.viewscreen.api.model.request.ipconfig.RequestIpInfo;
import com.h3c.vdi.viewscreen.api.model.response.ipconfig.ResponseIpInfo;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.service.ipconfig.IpConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/11/10 16:29
 * @Created by lgw2845
 */

@RequestMapping("/ipConfig")
@RestController
@Log4j2
@Api(tags = "ip-config-controller")
@Validated
public class IpConfigController {

    @Resource
    IpConfigService ipConfigService;

    @ApiOperation(value = "获取IP信息", notes = "获取局点IP信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "uuid", required = true, value = "uuid")
    })
    @GetMapping(value = "/ipInfo")
    public ApiResponse<ResponseIpInfo> ipInfo(@RequestParam("uuid") @Valid @NotBlank(message = "不能为空") String uuid) {
        return ipConfigService.ipInfo(uuid);
    }

    @ApiOperation(value = "修改IP信息", notes = "修改局点IP信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RequestIpInfo", name = "requestIpInfo", value = "请求参数")
    })
    @PostMapping(value = "/updIp")
    public ApiResponse<Boolean> updIp(@RequestBody @Valid RequestIpInfo requestIpInfo) {
        return ipConfigService.updIp(requestIpInfo);
    }


}
