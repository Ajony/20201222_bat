package com.h3c.vdi.viewscreen.web.auth;

import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseChinaArea;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.security.basic.ResponseUserDetails;
import com.h3c.vdi.viewscreen.service.auth.GlobalConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname GlobalConfigController
 * @Description TODO
 * @Date 2020/7/20 15:11
 * @Created by lgw2845
 */
@RestController
@RequestMapping("/globalConfig")
@Slf4j
@Api(tags = "global-config-controller")
public class GlobalConfigController {

    @Resource
    GlobalConfigService globalConfigService;

    @ApiOperation(value = "初始化", notes = "初始化配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "value", required = true, value = "初始化地区类型：0/全国，1/区域"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "text", value = "位置信息", required = true)
    })
    @GetMapping(value = "/initConfig")
    public ApiResponse<Boolean> initConfig(@RequestParam(value = "value") String value, @RequestParam(value = "text") String text) {
        return globalConfigService.initConfig(value, text);
    }


    @ApiOperation(value = "获取区域", notes = "初始化配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "addressCode", value = "地区编码")
    })
    @GetMapping(value = "/queryArea")
    public ApiResponse<List<ResponseChinaArea>> queryArea(@RequestParam(value = "addressCode", required = false) String addressCode) {
        return globalConfigService.queryArea(addressCode);
    }


    @ApiOperation(value = "设置默认页", notes = "设置默认页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "value", value = "地区类型：0/全国，1/区域"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "text", value = "位置信息")
    })
    @GetMapping(value = "/configDefaultPage")
    public ApiResponse<Boolean> configDefaultPage(@RequestParam(value = "value") String value,
                                                  @RequestParam(value = "text") String text) {
        return globalConfigService.configDefaultPage(value, text);
    }


    @ApiOperation(value = "查询初始化配置", notes = "查询初始化配置")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/queryInitCon")
    public ApiResponse<ResponseUserDetails> queryInitCon() {
        return globalConfigService.queryInitCon();
    }


}
