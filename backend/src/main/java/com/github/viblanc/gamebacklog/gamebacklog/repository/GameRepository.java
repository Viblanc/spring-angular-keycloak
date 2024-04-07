package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {}