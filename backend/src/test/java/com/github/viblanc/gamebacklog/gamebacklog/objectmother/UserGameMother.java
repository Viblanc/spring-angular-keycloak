package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;

public class UserGameMother {
    public static UserGame.UserGameBuilder complete() {
        return UserGame.builder()
                .game(GameMother.complete().build())
                .completed(false)
                .favourite(false)
                .rating(-1);
    }
}
