package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.GameMother;
import com.github.viblanc.gamebacklog.gamebacklog.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    GameService gameService;
    private Game game;

    @BeforeEach
    void setUp() {
        game = GameMother.complete().build();
    }

    @Test
    public void findById() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        assertThat(gameService.findById(game.getId())).contains(game);
    }

    @Test
    public void existsById() {
        when(gameRepository.existsById(game.getId())).thenReturn(true);
        assertThat(gameService.existsById(game.getId())).isTrue();
        when(gameRepository.existsById(game.getId())).thenReturn(false);
        assertThat(gameService.existsById(game.getId())).isFalse();
    }

    @Test
    public void save() {
        when(gameRepository.save(game)).thenReturn(game);
        assertThat(gameService.save(game)).isEqualTo(game);
    }

    @Test
    public void delete() {
        gameService.delete(game);
        verify(gameRepository, times(1)).delete(eq(game));
    }
}
