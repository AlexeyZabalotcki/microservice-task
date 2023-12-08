package com.specific.group.com.user.service.dto;

import com.specific.group.com.user.service.model.Role;

public record UpdateUserDto(
        Long id,
        String email,
        String password,
        Role role
) {
}
