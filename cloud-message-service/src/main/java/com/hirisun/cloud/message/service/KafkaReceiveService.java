package com.hirisun.cloud.message.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * @author zhoufeng
 * @version 1.0
 * @className ReceiveService
 * @data 2020/7/29 11:59
 * @description kafka信息订阅服务
 */
@EnableBinding(Sink.class)
@Slf4j
public class KafkaReceiveService {

    @StreamListener(Sink.INPUT)
    public void receive(Message<String> message) {
        log.info("<== payload:{}",message.getPayload());
    }


}
