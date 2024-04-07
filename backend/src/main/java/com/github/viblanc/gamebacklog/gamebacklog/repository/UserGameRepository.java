package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGame, UserGameId> {}
