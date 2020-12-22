package com.h3c.vdi.viewscreen.module.redis;

import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.module.kafka.Message;
import com.h3c.vdi.viewscreen.utils.redis.RedisConstant;
import com.h3c.vdi.viewscreen.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lgq
 * @since 2020/5/28 17:40
 */
@RestController
@Slf4j
@RequestMapping("/redis")
public class RedisWeb implements ApplicationContextAware {

    private final Set<String> beanNames = new HashSet<>(500);
    @Resource
    RedisUtil redisUtil;

    @RequestMapping(value = "/setRedis", method = RequestMethod.GET)
    public ApiResponse<Boolean> setRedisKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        // redisUtil.setDataBase(RedisConstant.REDIS_DB_15);
        redisUtil.set(key, value);
        return ApiResponse.buildSuccess(true);
    }


    @RequestMapping(value = "/getRedis", method = RequestMethod.GET)
    public ApiResponse<String> getRedisKey(String key) {
        Object o = redisUtil.get(key);
        return ApiResponse.buildSuccess(o.toString());
    }


    @RequestMapping(value = "/setRedisKeyDB",method = RequestMethod.GET)
    public ApiResponse<Boolean> setRedisKeyDB2(@RequestParam("key") String key,@RequestParam("value") String value){
        redisUtil.setDataBase(RedisConstant.REDIS_DB_16);
        redisUtil.set(key,value,120L);
        return ApiResponse.buildSuccess(true);
    }

    @RequestMapping(value = "/setRedisMsg", method = RequestMethod.GET)
    public ApiResponse<Message> getRedisKey() {
        Message build = Message.builder()
                .id(System.currentTimeMillis())
                .msg("=================redis=============")
                .sendTime(new Date())
                .build();
        redisUtil.set("message", build);
        return ApiResponse.buildSuccess(build);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanNames.addAll(Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }


    @GetMapping("obtain.bean.names")
    public ApiResponse<?> obtainBeanNames() {
        return ApiResponse.buildSuccess(beanNames);
    }
}
