package com.specific.group.com.user.service.controller;

import com.specific.group.com.user.service.dto.*;
import com.specific.group.com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService service;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), ResponseUserDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.info("findById controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.findById(Long.parseLong(id)), ResponseUserDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByEmail(ServerRequest serverRequest) {
        log.info("findByEmail controller");
        String email = serverRequest.pathVariable("email");
        Mono<ResponseUserDto> userDto = service.findByEmail(email);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDto, ResponseUserDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info("save controller");
        Mono<UserDto> userDtoMono = serverRequest.bodyToMono(UserDto.class);
        log.info("controller User boby: {}", userDtoMono);

        return userDtoMono.flatMap(userDto -> {
            Mono<ResponseUserDto> saved = service.save(userDto);
            return ServerResponse.ok()
                    .body(saved, ResponseUserDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        log.info("save controller");
        Mono<UpdateUserDto> userDtoMono = serverRequest.bodyToMono(UpdateUserDto.class);

        return userDtoMono.flatMap(userDto -> {
            Mono<ResponseUserDto> updated = service.update(userDto);
            return ServerResponse.ok()
                    .body(updated, ResponseUserDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    public Mono<ServerResponse> authenticate(ServerRequest serverRequest) {
        log.info("authenticate controller");
        Mono<LoginDto> dtoMono = serverRequest.bodyToMono(LoginDto.class);
        log.info("controller authenticate User boby: {}", dtoMono);

        return dtoMono.flatMap(loginDto -> {
            Mono<AuthenticationResponseDto> authenticate = service.authenticate(loginDto);
            return ServerResponse.ok()
                    .body(authenticate, AuthenticationResponseDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    public Mono<ServerResponse> register(ServerRequest serverRequest) {
        log.info("register controller");
        Mono<RegisterDto> dtoMono = serverRequest.bodyToMono(RegisterDto.class);
        log.info("controller register User boby: {}", dtoMono);

        return dtoMono.flatMap(registerDto -> {
            Mono<AuthenticationResponseDto> registered = service.register(registerDto);
            return ServerResponse.ok()
                    .body(registered, AuthenticationResponseDto.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        log.info("delete controller");
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .body(service.deleteById(Long.parseLong(id)), UserDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
