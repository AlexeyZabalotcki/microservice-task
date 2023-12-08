package com.specific.group.com.user.service.dao;

import com.specific.group.com.user.service.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    @Query("SELECT * FROM \"users\"")
    Flux<User> findAll();

    @Query("INSERT INTO users (email, password, role) VALUES (:#{#user.email}, :#{#user.password}, :#{#user.role}) RETURNING *")
    Mono<User> save(@Param("user") User user);

    @Query("UPDATE users SET email = :#{#user.email}, password = :#{#user.password}, role = :#{#user.role} WHERE id = :#{#user.id} RETURNING *")
    Mono<User> update(@Param("user") User user);

    @Query("DELETE FROM users WHERE id = :id")
    Mono<Void> deleteById(@Param("id") Long id);

    @Query("SELECT * FROM users WHERE id = :id")
    Mono<User> findById(@Param("id") Long id);

    @Query("SELECT * FROM users WHERE email = :email")
    Mono<User> findByEmail(@Param("email") String email);
}
