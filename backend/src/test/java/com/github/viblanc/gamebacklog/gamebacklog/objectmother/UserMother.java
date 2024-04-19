package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;

public class UserMother {
    public static User.UserBuilder base() {
        return User.builder()
                .username("test_user");
    }
}
