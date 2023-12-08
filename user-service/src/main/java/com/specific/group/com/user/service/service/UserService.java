package com.specific.group.com.user.service.service;

import com.specific.group.com.user.service.dto.AuthenticationResponseDto;
import com.specific.group.com.user.service.dto.LoginDto;
import com.specific.group.com.user.service.dto.RegisterDto;
import com.specific.group.com.user.service.dto.ResponseUserDto;
import com.specific.group.com.user.service.dto.UpdateUserDto;
import com.specific.group.com.user.service.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<ResponseUserDto> findAll();

    Mono<ResponseUserDto> save(UserDto user);

    Mono<ResponseUserDto> update(UpdateUserDto user);

    Mono<Void> deleteById(Long id);

    Mono<ResponseUserDto> findById(Long id);

    Mono<ResponseUserDto> findByEmail(String email);

    Mono<AuthenticationResponseDto> register(RegisterDto registerRequest);

    Mono<AuthenticationResponseDto> authenticate(LoginDto registerRequest);
}
