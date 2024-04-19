package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;

public class UserGameMother {
    public static UserGame.UserGameBuilder base() {
        return UserGame.builder()
                .game(GameMother.complete().build())
                .completed(false)
                .favourite(false)
                .rating(-1);
    }

    public static UserGame.UserGameBuilder complete() {
        User user = UserMother.base().build();
        Game game = GameMother.complete().build();
        return UserGame.builder()
                .id(new UserGameId(user.getUsername(), game.getId()))
                .user(user)
                .game(game)
                .completed(false)
                .favourite(false)
                .rating(-1);
    }
}
