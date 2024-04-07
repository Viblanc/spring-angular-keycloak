package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository repository;

    public Optional<Game> findById(Long id) {
        return repository.findById(id);
    }

    public boolean existsById(Long id) { return repository.existsById(id); }

    public Game save(Game game) {
        return repository.save(game);
    }

    public void delete(Game game) {
        repository.delete(game);
    }
}
