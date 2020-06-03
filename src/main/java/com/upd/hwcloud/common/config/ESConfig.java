package com.upd.hwcloud.common.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

    @Value("${es.hostname}")
    private String hostname;
    @Value("${es.port}")
    private int port;
    @Value("${es.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient highLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostname, port, scheme)));
        return highLevelClient;
    }

}
