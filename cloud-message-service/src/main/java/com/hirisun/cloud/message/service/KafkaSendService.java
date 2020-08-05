package com.hirisun.cloud.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author zhoufeng
 * @version 1.0
 * @className KafkaSendService
 * @data 2020/7/29 11:51
 * @description kafka消息发送服务
 */
@EnableBinding(Source.class)
public class KafkaSendService {

    @Autowired
    private Source source;

    public void sendMsg(String msg) {
        source.output().send(MessageBuilder.withPayload(msg).build());
    }
}
