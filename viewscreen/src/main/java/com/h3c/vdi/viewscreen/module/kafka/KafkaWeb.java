package com.h3c.vdi.viewscreen.module.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lgq
 * @since 2020/5/27 19:13
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaWeb {

//    @Resource
//    private KafkaTemplate kafkaTemplate;
//
//   /* @Resource
//    private Producer producer;*/
//
//    Gson gson = new Gson().newBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();
//
//    @RequestMapping("/send")
//    public ApiResponse<Message> send() {
//        Message message = Message.builder()
//                .id(System.currentTimeMillis())
//                .msg(UUID.randomUUID().toString() + "====================Kafka=============send==============")
//                .sendTime(new Date())
//                .build();
//            log.info("发送消息 ----->>>>>  message = {}", gson.toJson(message));
//        kafkaTemplate.send("hello", gson.toJson(message));
//        return ApiResponse.buildSuccess(message);
//    }



   /* @RequestMapping("/sendMsg")
    public ApiResponse<Boolean> sendMsg(){
        producer.send();
        return ApiResponse.buildSuccess(true);
    }*/


}
