package com.specific.group.com.user.service.dto;

import com.specific.group.com.user.service.model.Role;

public record ResponseUserDto(
        String email,
        Role role
) {
}
