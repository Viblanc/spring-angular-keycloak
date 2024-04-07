package com.github.viblanc.gamebacklog.gamebacklog.dto;

import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private List<UserGameDto> games;
}
