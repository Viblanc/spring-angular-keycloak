package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;
import com.github.viblanc.gamebacklog.gamebacklog.repository.UserGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGameService {
    private final UserGameRepository repository;

    public Optional<UserGame> findById(UserGameId id) {
        return repository.findById(id);
    }

    public UserGame save(UserGame userGame) {
        return repository.save(userGame);
    }

    public void delete(UserGame userGame) {
        repository.delete(userGame);
    }
}
