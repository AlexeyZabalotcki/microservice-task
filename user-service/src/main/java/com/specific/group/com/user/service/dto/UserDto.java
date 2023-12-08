package com.specific.group.com.user.service.dto;

import com.specific.group.com.user.service.model.Role;

public record UserDto(
        String email,
        String password,
        Role role
) {
}
