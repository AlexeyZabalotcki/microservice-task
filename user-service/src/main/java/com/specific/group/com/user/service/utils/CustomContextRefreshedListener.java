package com.specific.group.com.user.service.utils;

import com.specific.group.com.user.service.dao.UserRepository;
import com.specific.group.com.user.service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.specific.group.com.user.service.model.Role.ADMIN;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository repository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null){
            User users = User.builder()
                    .email("admin@gmail.com")
                    .password("$2a$12$yYV8Twx2CcYwMUpawzRrSe6gQXAwOevpaCbF2i4/2z3mEM.1VwJ2m")
                    .role(ADMIN)
                    .build();

            repository.save(users)
                    .subscribe(savedUser -> {
                        log.info("User inserted from ContextRefreshedListener: {}", savedUser);
                    });
        }

    }
}
