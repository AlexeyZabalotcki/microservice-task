package com.specific.group.com.user.service.service.impl;

import com.specific.group.com.user.service.dao.UserRepository;
import com.specific.group.com.user.service.dto.*;
import com.specific.group.com.user.service.mapper.UserMapper;
import com.specific.group.com.user.service.model.Role;
import com.specific.group.com.user.service.model.User;
import com.specific.group.com.user.service.service.UserService;
import com.specific.group.com.user.service.token.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static com.specific.group.com.user.service.model.Role.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Flux<ResponseUserDto> findAll() {
        Flux<User> users = repository.findAll();
        return users
                .flatMap(user -> Flux.just(mapper.toResponseUserDto(user)));
    }

    @Override
    @Transactional
    public Mono<ResponseUserDto> save(UserDto dto) {
        log.info("service User boby: {}", dto);
        User user = mapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String email = user.getEmail();

        log.info("service register User: {}", user);
        return repository.findByEmail(email)
                .flatMap(user1 -> {
                    if (email.equals(user1.getEmail())) {
                        throw new IllegalArgumentException("Email already exists");
                    }
                    return Mono.empty();
                })
                .switchIfEmpty(repository.save(user)
                        .doOnSuccess(saved -> log.info("Saved user: {}", saved)))
                .cast(User.class)
                .map(mapper::toResponseUserDto)
                .flatMap(Mono::just);
    }

    @Override
    @Transactional
    public Mono<ResponseUserDto> update(UpdateUserDto dto) {
        User userToUpdate = mapper.toUserUpdate(dto);
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        Mono<User> update = repository.update(userToUpdate);
        return update.
                flatMap(user -> Mono.just(mapper.toResponseUserDto(user)));
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<ResponseUserDto> findById(Long id) {
        Mono<User> user = repository.findById(id);
        return user
                .flatMap(toDto -> Mono.just(mapper.toResponseUserDto(toDto)));
    }

    @Override
    public Mono<ResponseUserDto> findByEmail(String email) {
        Mono<User> user = repository.findByEmail(email);
        return user
                .flatMap(toDto -> Mono.just(mapper.toResponseUserDto(toDto)));
    }

    @Override
    @Transactional
    public Mono<AuthenticationResponseDto> register(RegisterDto request) {
        log.info("service register User body: {}", request);
        User user = getUser(request);
        String email = user.getEmail();

        log.info("service register User: {}", user);
        return repository.findByEmail(email)
                .flatMap(user1 -> {
                    if (email.equals(user1.getEmail())) {
                        throw new IllegalArgumentException("Email already exists");
                    }
                    return Mono.empty();
                })
//                .onErrorResume(throwable -> Mono.error(new IllegalArgumentException("Email already exists")))
                .switchIfEmpty(repository.save(user)
                        .doOnSuccess(saved -> log.info("Saved user: {}", saved)))
                .cast(User.class)
                .map(jwtService::generateAccessToken)
                .map(AuthenticationResponseDto::new);
    }

    @Override
    @Transactional
    public Mono<AuthenticationResponseDto> authenticate(LoginDto request) {
        log.info("service authenticate User body: {}", request);
        return repository.findByEmail(request.email())
                .flatMap(user -> {
                    var matches = passwordEncoder.matches(request.password(), user.getPassword());
                    log.info("service authenticate password matches?: {}", matches);
                    if (matches) {
                        var jwtAccessToken = jwtService.generateAccessToken(user);
                        return Mono.just(new AuthenticationResponseDto(jwtAccessToken));
                    } else {
                        throw new IllegalArgumentException("Incorrect password");
                    }
                });
//                .onErrorResume(throwable -> Mono.error(new IllegalArgumentException("Incorrect password")));
    }

    private User getUser(RegisterDto request) {
        return User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
    }
}
