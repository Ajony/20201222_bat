package com.h3c.vdi.viewscreen.module.kafka;

/*import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

*//**
 * @author lgq
 * @since 2020/5/28 10:28
 *//*
@Component
@Slf4j
public class Producer {

    @Resource
    private KafkaTemplate kafkaTemplate;

    Gson gson = new Gson().newBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();

    //发送消息方法
    public void send() {
        for (int i = 0; i < 5; i++) {
            Message message = Message.builder()
                    .id(System.currentTimeMillis())
                    .msg(UUID.randomUUID().toString() + "====================Kafka=============send==============" + i)
                    .sendTime(new Date())
                    .build();
            log.info("发送消息 ----->>>>>  message = {}", gson.toJson(message));
            kafkaTemplate.send("hello", gson.toJson(message));
        }
    }


}*/
