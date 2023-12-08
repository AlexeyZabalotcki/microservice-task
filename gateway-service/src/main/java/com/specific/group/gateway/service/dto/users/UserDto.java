package com.specific.group.gateway.service.dto.users;

public record UserDto(
        String email,
        String password,
        Role role
) {
}
