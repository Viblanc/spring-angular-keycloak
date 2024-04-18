package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;

import java.util.List;

public class UserMother {
    public static User.UserBuilder base() {
        return User.builder()
                .username("test_user");
    }

    public static User.UserBuilder withGames() {
        return User.builder()
                .username("test_user")
                .games(List.of(UserGameMother.complete().build()));
    }
}
