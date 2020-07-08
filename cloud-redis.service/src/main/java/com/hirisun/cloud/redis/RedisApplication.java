package com.hirisun.cloud.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoufeng
 * @version 1.0
 * @className RedisApplication
 * @data 2020/7/6 9:53
 * @description Redis 服务主启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }
}
