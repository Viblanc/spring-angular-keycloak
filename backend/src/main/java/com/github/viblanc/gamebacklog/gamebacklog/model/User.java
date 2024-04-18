package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String igdbAccessToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGame> games;

    public User(String username) {
        this.username = username;
        this.games = new ArrayList<>();
    }
}