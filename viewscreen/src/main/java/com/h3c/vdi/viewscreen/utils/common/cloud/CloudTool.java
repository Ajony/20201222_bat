package com.h3c.vdi.viewscreen.utils.common.cloud;

import com.h3c.vdi.viewscreen.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@ConditionalOnProperty(name = "vdi.service.provider.viewscreen", havingValue = Constant.EnvPath.CLOUD_ON)
public class CloudTool {


    @Resource
    private RestTemplate restTemplate;

    private static String token;

    private static final String HTTP_KEYSTONE_TOKEN_URL = "http://os-keystone-svc.default.svc.cloudos:35357/v3/auth/tokens";


    public static String getToken() {
        return token;
    }

    @PostConstruct
    public void init() {

        /**
         * 保证获取到token为止
         */
        ScheduledExecutorService refreshTokenExecutor
                = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("refresh-cloudosToken-schedule-pool-%d").build());
        refreshTokenExecutor.execute(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    refreshToken();
                    break;
                } catch (Exception e) {
                    log.error("get token error! msg: {}", e.getMessage());
                }
            }
        });
        /**
         * token并不是一成不变的，默认情况下4个小时就会过期，因此每小时刷新一次token
         */
        refreshTokenExecutor.scheduleAtFixedRate(() -> {
            try {
                refreshToken();
            } catch (Exception e) {
                log.error("refreshToken error:", e);
            }
        }, 1, 1, TimeUnit.HOURS);

    }

    private void refreshToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        //请求体内容为固定json串
        String bodyJson = "{\n" +
                "    \"auth\": {\n" +
                "        \"identity\": {\n" +
                "            \"methods\": [\n" +
                "                \"password\"\n" +
                "            ],\n" +
                "            \"password\": {\n" +
                "                \"user\": {\n" +
                "                    \"domain\": {\n" +
                "                        \"name\": \"default\"\n" +
                "                    },\n" +
                "                    \"name\": \"vdiControllerAdmin\",\n" +
                "                    \"password\": \"cloudos\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"scope\": {\n" +
                "            \"project\": {\n" +
                "                \"domain\": {\n" +
                "                    \"name\": \"default\"\n" +
                "                },\n" +
                "                \"name\": \"service\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        HttpEntity httpEntity = new HttpEntity(bodyJson, headers);
        List<String> tokenHeaderList = restTemplate.exchange(HTTP_KEYSTONE_TOKEN_URL, HttpMethod.POST, httpEntity, Void.class).getHeaders().get("X-Subject-Token");
        log.info("url get token success: {}",HTTP_KEYSTONE_TOKEN_URL);
        if (CollectionUtils.isNotEmpty(tokenHeaderList)) {
            token = tokenHeaderList.get(0);
            log.info("get token success: {}", token);
        } else {
            log.info("get token failed: {}", tokenHeaderList);
        }
    }


}
