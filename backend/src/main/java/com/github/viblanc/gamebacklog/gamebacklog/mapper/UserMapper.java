package com.github.viblanc.gamebacklog.gamebacklog.mapper;

import com.github.viblanc.gamebacklog.gamebacklog.dto.UserDto;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .games(user.getGames().stream().map(UserGameMapper::toDto).toList())
                .build();
    }
}
