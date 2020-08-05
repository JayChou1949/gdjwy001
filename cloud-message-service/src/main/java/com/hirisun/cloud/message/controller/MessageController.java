package com.hirisun.cloud.message.controller;

import com.hirisun.cloud.message.service.KafkaSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoufeng
 * @version 1.0
 * @className MessageController
 * @data 2020/7/29 11:53
 * @description 消息发送控制器
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    KafkaSendService kafkaSendService;

    @PostMapping("/send")
    public String send(@RequestParam("message") String message){
        kafkaSendService.sendMsg(message);
        return "ok";
    }
}
