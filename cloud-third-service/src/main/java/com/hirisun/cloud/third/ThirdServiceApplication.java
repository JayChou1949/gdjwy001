package com.hirisun.cloud.third;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.hirisun.cloud"})
@EnableDiscoveryClient
@EnableScheduling //启用定时服务
@MapperScan("com.hirisun.cloud.third.mapper")
public class ThirdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdServiceApplication.class, args);
    }

}
