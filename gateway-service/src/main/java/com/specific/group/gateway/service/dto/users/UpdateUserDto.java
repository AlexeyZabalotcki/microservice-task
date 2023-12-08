package com.specific.group.gateway.service.dto.users;

public record UpdateUserDto(
        Long id,
        String email,
        String password,
        Role role
) {
}
