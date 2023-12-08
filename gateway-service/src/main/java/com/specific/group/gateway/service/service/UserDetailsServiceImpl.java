package com.specific.group.gateway.service.service;

import com.specific.group.gateway.service.feing_client.UserMsReactiveFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.specific.group.gateway.service.security.JwtUser;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserMsReactiveFeignClient userMsReactiveFeignClient;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userMsReactiveFeignClient.findUserByUsername(email)
                .map(JwtUser::new);
    }
}
