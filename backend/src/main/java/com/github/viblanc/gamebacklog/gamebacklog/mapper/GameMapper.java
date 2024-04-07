package com.github.viblanc.gamebacklog.gamebacklog.mapper;

import com.github.viblanc.gamebacklog.gamebacklog.dto.GameDto;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;

public class GameMapper {
    public static GameDto toDto(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .name(game.getName())
                .releaseDate(game.getReleaseDate())
                .coverUrl(game.getCoverUrl())
                .summary(game.getSummary())
                .platforms(game.getPlatforms().stream().map(PlatformMapper::toDto).toList())
                .build();
    }

    public static Game fromDto(GameDto gameDto) {
        return Game.builder()
                .id(gameDto.getId())
                .name(gameDto.getName())
                .releaseDate(gameDto.getReleaseDate())
                .coverUrl(gameDto.getCoverUrl())
                .summary(gameDto.getSummary())
                .platforms(gameDto.getPlatforms().stream().map(PlatformMapper::fromDto).toList())
                .build();
    }
}
