package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserGameId implements Serializable {
    @Column(name = "username")
    private String username;
    @Column(name = "game_id")
    private Long gameId;
}
