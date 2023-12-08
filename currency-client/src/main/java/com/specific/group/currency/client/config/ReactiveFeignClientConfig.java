package com.specific.group.currency.client.config;

import com.specific.group.currency.client.service.feign_client.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveFeignClientConfig {
    @Bean
    public CustomErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }

}
