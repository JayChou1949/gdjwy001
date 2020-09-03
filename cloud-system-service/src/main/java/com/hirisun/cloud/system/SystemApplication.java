package com.hirisun.cloud.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.hirisun.cloud","com.dragonsoft.mq"})
@MapperScan("com.hirisun.cloud.system.mapper")
@EnableFeignClients("com.hirisun.cloud.api.*")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
