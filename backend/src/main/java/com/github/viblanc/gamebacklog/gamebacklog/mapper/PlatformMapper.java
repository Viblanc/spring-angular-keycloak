package com.github.viblanc.gamebacklog.gamebacklog.mapper;

import com.github.viblanc.gamebacklog.gamebacklog.dto.PlatformDto;
import com.github.viblanc.gamebacklog.gamebacklog.model.Platform;

public class PlatformMapper {
    public static PlatformDto toDto(Platform platform) {
        return PlatformDto.builder()
                .id(platform.getId())
                .name(platform.getName())
                .alternativeName(platform.getAlternativeName())
                .abbreviation(platform.getAbbreviation())
                .build();
    }

    public static Platform fromDto(PlatformDto platformDto) {
        return Platform.builder()
                .id(platformDto.getId())
                .name(platformDto.getName())
                .alternativeName(platformDto.getAlternativeName())
                .abbreviation(platformDto.getAbbreviation())
                .build();
    }
}
