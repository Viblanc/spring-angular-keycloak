package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_game")
public class UserGame {
    @EmbeddedId
    private UserGameId id;
    @ManyToOne
    @MapsId("username")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("gameId")
    private Game game;
    private boolean completed;
    private boolean favourite;
    private int rating;
}
