package com.hirisun.cloud.ncov;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.hirisun.cloud"})
@EnableDiscoveryClient
@MapperScan("com.hirisun.cloud.ncov.mapper")
@EnableFeignClients("com.hirisun.cloud.api.*")
public class NcovApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcovApplication.class,args);

	}
}
