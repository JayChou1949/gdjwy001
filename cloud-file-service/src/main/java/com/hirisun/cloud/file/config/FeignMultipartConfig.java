package com.hirisun.cloud.file.config;

import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FeignMultipartConfig
 * @data 2020/8/5 11:11
 * @description FeignMultipartFile配置类
 */
@Configuration
public class FeignMultipartConfig {
    @Bean
    @Primary
    @Scope("prototype")
    public SpringFormEncoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }
}
