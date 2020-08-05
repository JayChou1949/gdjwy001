package com.hirisun.cloud.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoufeng
 * @version 1.0
 * @className MessageApplication
 * @data 2020/7/28 15:25
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.hirisun.cloud"},exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class,args);
    }
}
