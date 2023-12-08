package com.specific.group.gateway.service.config;

import com.specific.group.gateway.service.feing_client.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveFeignClientConfig {
    @Bean
    public CustomErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }

}
