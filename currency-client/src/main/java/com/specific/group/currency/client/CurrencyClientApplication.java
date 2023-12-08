package com.specific.group.currency.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableReactiveFeignClients
public class CurrencyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyClientApplication.class, args);
	}

}
