package com.github.viblanc.gamebacklog.gamebacklog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGameDto {
    private GameDto game;
    private boolean completed;
    private boolean favourite;
    private int rating;
}
