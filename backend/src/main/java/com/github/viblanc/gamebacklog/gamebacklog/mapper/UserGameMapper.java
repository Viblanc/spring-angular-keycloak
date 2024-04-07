package com.github.viblanc.gamebacklog.gamebacklog.mapper;

import com.github.viblanc.gamebacklog.gamebacklog.dto.UserGameDto;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;

public class UserGameMapper {
    public static UserGameDto toDto(UserGame userGame) {
        return UserGameDto.builder()
                .game(GameMapper.toDto(userGame.getGame()))
                .completed(userGame.isCompleted())
                .favourite(userGame.isFavourite())
                .rating(userGame.getRating())
                .build();
    }
}
