package com.h3c.vdi.viewscreen.web.report;

import com.h3c.vdi.viewscreen.api.model.request.report.RequestMac;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReport;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReportShareArea;
import com.h3c.vdi.viewscreen.api.model.response.computenode.CloudOSCalulateNode;
import com.h3c.vdi.viewscreen.api.model.response.computenode.CloudOSResult;
import com.h3c.vdi.viewscreen.api.model.response.report.ResponseReport;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.service.report.ReportService;
import com.h3c.vdi.viewscreen.utils.common.IpUtil;
import com.h3c.vdi.viewscreen.utils.common.cloud.CloudRestClient;
import com.h3c.vdi.viewscreen.utils.common.cloud.CloudTool;
import com.h3c.vdi.viewscreen.utils.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lgq
 * @since 2020/6/3 11:03
 */
@RequestMapping("/report")
@RestController
@Slf4j
@Api(tags = "report-controller")
@Validated
@SuppressWarnings("rawtypes")
public class ReportController {


    @Resource
    private ReportService reportService;

    @Autowired(required = false)
    private CloudRestClient restClient;

    @Value("${vdi.service.provider.viewscreen}")
    private String provider;

    @Resource
    private HttpServletRequest servletRequest;


    @ApiOperation(value = "校验uuid接口", notes = "根据mac和uuid校验uuid")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RequestMac", name = "requestMac", value = "请求参数")
    })
    @PostMapping(value = "/checkUuid")
    public ApiResponse<ResponseReport> checkUuid(@RequestBody @Valid RequestMac requestMac) {
        return reportService.checkUuid(requestMac);
    }


    @ApiOperation(value = "打开或关闭上报接口", notes = "打开或关闭上报接口:open，close")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "status", value = "请求参数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "uuid", value = "请求参数", required = true)
    })
    @GetMapping(value = "/reportSwitch")
    public ApiResponse<Boolean> reportSwitch(@RequestParam("status") String status, @RequestParam("uuid") String uuid) {
        return reportService.reportSwitch(status, uuid);
    }


    @ApiOperation(value = "局点上报接口", notes = "上报局点数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RequestReportShareArea", name = "requestReport", value = "请求参数")
    })
    @PostMapping(value = "/reportShareArea")
    public ApiResponse<Boolean> reportShareArea(@RequestBody @Valid RequestReportShareArea requestReport) {
        requestReport.setIpList(requestReport.getIp());
        //todo 基于云下上报局点
        if (provider.equals(Constant.EnvPath.CLOUD_DOWN)) {
            log.info("云下局点上报:START success {}", requestReport);
            setIp(requestReport);
            log.info("----------------------------->: {}", provider + "：" + requestReport.getIp());
            return reportService.reportShareArea(requestReport);
        }

        //todo   基于云上上报局点
        if (provider.equals(Constant.EnvPath.CLOUD_ON)) {
            log.info("云上局点上报:START success {}", requestReport);
            List<String> varCvmIp = queryCvmIp();
//            List<String> varCasIp = casIpList();

            if (CollectionUtils.isNotEmpty(varCvmIp) && StringUtils.isNotBlank(requestReport.getIp())) {

                if (requestReport.getIp().contains(",")) {
                    log.info("split ip address : success {}", varCvmIp + "-----------------------------" + requestReport.getIp());
                    String[] split = requestReport.getIp().split(",");
                    for (String var : varCvmIp) {
                        for (String s : split) {
                            if (var.equals(s)) {
                                requestReport.setIp(s);
                                return reportService.reportShareArea(requestReport);
                            }
                        }
                    }
                } else {
                    log.info("nonSplit ip address: success {}", varCvmIp + "-----------------------------" + requestReport.getIp());
                    for (String var : varCvmIp) {
                        if (var.equals(requestReport.getIp())) {
                            return reportService.reportShareArea(requestReport);
                        }
                    }
                }
                log.info("IP THERE IS NO INTERSECTION ERROR: {}", "云上casIP列表与workspace上报IP列表未有交集");
                return null;
            }
            log.info("GET cloudOs CAS_IP SUCCESS: {}", varCvmIp);
            log.info("GET workSpace IP SUCCESS: {}", requestReport.getIp());
            return null;
        }

        //todo 本地调试
        if (StringUtils.isBlank(requestReport.getIp())) {
            setIp(requestReport);
        }
        log.info("本地局点上报:START success {}", requestReport);
        return reportService.reportShareArea(requestReport);
    }

    private void setIp(RequestReportShareArea requestReport) {
        String ipAddress = IpUtil.getIpAddress(servletRequest);
        if (ipAddress.equals(IpUtil.ERROR_IP)) {
            ipAddress = requestReport.getIp();
            if (requestReport.getIp().contains(",")) {
                ipAddress = Stream.of(requestReport.getIp().split(",")).collect(Collectors.toList()).get(0);
            }
        }
        requestReport.setIp(ipAddress);
    }


    //获取cloudOS_纳管的casIP
    public List<String> queryCvmIp() {
        String url = Constant.Cloudos.API_CALULATE_NODELIST;
        CloudOSResult<List<CloudOSCalulateNode>> cloudOSResult = new CloudOSResult<>();
        try {
            cloudOSResult = restClient.get(url, restClient.tokenHeader(CloudTool.getToken()), new ParameterizedTypeReference<CloudOSResult<List<CloudOSCalulateNode>>>() {
            }).getBody();
        } catch (Exception e) {
            log.error(null, e);
        }
        if (cloudOSResult != null) {
            log.info(cloudOSResult.toString());
        } else {
            log.info("get cloudos CvmIP error.....................................");
        }
        if (Objects.nonNull(cloudOSResult) && CollectionUtils.isNotEmpty(cloudOSResult.getData())) {
            List<CloudOSCalulateNode> data = cloudOSResult.getData();
            List<String> collect = data.stream().filter(item -> item.getVmType() == 2).map(CloudOSCalulateNode::getHostIp).collect(Collectors.toList());
            log.info("get CvmIP list: {}", collect);
            return collect;
        }
        return null;
    }

    //获取cloudOS_纳管的casIP
    public List<String> casIpList() {
        String url = Constant.Cloudos.API_CALULATE_NODELIST;
        CloudOSResult<List<Map<String, Object>>> cloudOSResult = restClient.get(url, CloudOSResult.class).getBody();

        if (cloudOSResult != null) {
            log.info(cloudOSResult.toString());
        } else {
            log.info("get cloudos CvmIP error.....................................");
        }
        if (Objects.nonNull(cloudOSResult) && CollectionUtils.isNotEmpty(cloudOSResult.getData())) {
            List<Map<String, Object>> data = cloudOSResult.getData();
            List<String> collect = data.stream().filter(item -> item.get("vmType").equals(Integer.valueOf(2)))
                    .map(item -> (String) item.get("hostIp")).collect(Collectors.toList());
            log.info("get casIP list: {}", collect);
            return collect;
        }
        return null;
    }


    @ApiOperation(value = "数据上报接口", notes = "根据指定类型上报数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "uuid", value = "请求参数", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "RequestReport", name = "requestReport", value = "请求参数", allowMultiple = true)
    })
    @PostMapping(value = "/reportData")
    public ApiResponse<ResponseReport> reportData(@RequestBody @Valid @NotEmpty(message = "上报数据不能为空") List<RequestReport> requestReport,
                                                  @RequestParam("uuid") @NotBlank(message = "UUID不能为空") String uuid) {
        return reportService.reportData(requestReport, uuid);
    }


    @Resource
    RedisUtil redisUtil;

    @GetMapping(value = "/redisLock")
    public String redisLock(@RequestParam("key") String key) throws InterruptedException {

        //设置锁过期时间，避免造成死锁
        if (redisUtil.setIfPresent(key, "lock", 100)) {
            System.out.println("myKey 上锁................." + key);

            Thread.sleep(60000);
            redisUtil.del(key);

            System.out.println("myKey 释放锁................." + key);
            return "myKey 测试中................." + key;
        }

        System.out.println("锁资源占用中" + key);
        return "锁资源占用中";
    }

    public static void main(String[] args) {

        System.out.println(LocalDateTime
                .ofEpochSecond(Long.parseLong("1607073620000") / 1000, 0, ZoneOffset.ofHours(8))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
