package com.specific.group.com.user.service.config;

import com.specific.group.com.user.service.dao.UserRepository;
import com.specific.group.com.user.service.model.User;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.specific.group.com.user.service.model.Role.USER;

@Slf4j
@Configuration
public class Config implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        User users = User.builder()
                .email("user@gmail.com")
                .password("$2a$12$5A6UamQ9z5KGTx5cOFzpGOyWXXRKLz4owf08oEFxL6FuQHhbDZHOa")
                .role(USER)
                .build();

        repository.save(users)
                .subscribe(savedUser -> {
                    log.info("User inserted from Config: {}", savedUser);
                });
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("db/changelog/db.changelog-init.yaml")));
        return initializer;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
