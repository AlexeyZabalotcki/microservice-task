package com.specific.group.com.user.service;

import com.specific.group.com.user.service.dao.UserRepository;
import com.specific.group.com.user.service.model.User;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;

import static com.specific.group.com.user.service.model.Role.ADMIN;
import static com.specific.group.com.user.service.model.Role.USER;

@EnableWebFlux
@SpringBootApplication
@EnableEurekaClient
@EnableR2dbcRepositories
public class UserServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}


}
