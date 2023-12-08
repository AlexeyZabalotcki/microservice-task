package com.specific.group.gateway.service.feing_client;

import com.specific.group.gateway.service.dto.users.AuthenticationResponseDto;
import com.specific.group.gateway.service.dto.users.LoginDto;
import com.specific.group.gateway.service.dto.users.RegisterDto;
import com.specific.group.gateway.service.dto.users.UpdateUserDto;
import com.specific.group.gateway.service.dto.users.User;
import com.specific.group.gateway.service.dto.users.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.specific.group.gateway.service.constants.UsersConstants.USER_SERVICE;

@Service
@ReactiveFeignClient(name = USER_SERVICE)
public interface UserMsReactiveFeignClient {

    @GetMapping("/api/v1/users/email/{email}")
    Mono<User> findUserByUsername(@PathVariable String email);

    @PostMapping("/api/v1/users/sign-in")
    Mono<AuthenticationResponseDto> register(RegisterDto registerDto);

    @PostMapping("/api/v1/users/authenticate")
    Mono<AuthenticationResponseDto> authenticate(LoginDto loginDto);

    @GetMapping("/api/v1/users")
    Mono<List<UserDto>> findAll();

    @GetMapping("/api/v1/users/{id}")
    Mono<UserDto> findById(@PathVariable Long id);

    @PostMapping("/api/v1/users")
    Mono<UserDto> save(UserDto user);

    @PutMapping("/api/v1/users/update")
    Mono<UserDto> update(UpdateUserDto updates);

    @DeleteMapping("/api/v1/users/{id}")
    Mono<UserDto> deleteById(@PathVariable("id") Long id);
}
