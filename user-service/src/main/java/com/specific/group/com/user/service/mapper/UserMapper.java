package com.specific.group.com.user.service.mapper;

import com.specific.group.com.user.service.dto.ResponseUserDto;
import com.specific.group.com.user.service.dto.UpdateUserDto;
import com.specific.group.com.user.service.dto.UserDto;
import com.specific.group.com.user.service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto userDto) {
        return User.builder()
                .email(userDto.email())
                .password(userDto.password())
                .role(userDto.role())
                .build();
    }
    public User toUserUpdate(UpdateUserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .email(userDto.email())
                .password(userDto.password())
                .role(userDto.role())
                .build();
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getEmail(), user.getPassword(), user.getRole());
    }

    public ResponseUserDto toResponseUserDto(User user) {
        return new ResponseUserDto(user.getEmail(), user.getRole());
    }
}
