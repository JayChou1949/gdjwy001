package com.hirisun.cloud.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileApplication
 * @data 2020/7/1 11:25
 * @description 文件服务主启动类
 */
@SpringBootApplication(scanBasePackages = {"com.hirisun.cloud"})
@MapperScan("com.hirisun.cloud.file.mapper")
@EnableDiscoveryClient
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }
}
