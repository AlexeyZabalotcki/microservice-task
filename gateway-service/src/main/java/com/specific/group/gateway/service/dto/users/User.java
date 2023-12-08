package com.specific.group.gateway.service.dto.users;

public record User(Long id,

                   String email,

                   String password,
                   Role role) {
}
