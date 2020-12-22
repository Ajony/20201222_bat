package com.h3c.vdi.viewscreen.module.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lgq
 * @since 2020/5/28 10:34
 */
@Component
@Slf4j
public class Consumer {


//    @KafkaListener(topics = {"hello"})
//    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {
//        Optional<?> kafkaMessage;
//        kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//            log.info("========================== record =" + record);
//            log.info("========================== message =" + message);
//        }
//        ack.acknowledge();
//    }


}
