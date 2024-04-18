package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserGameMother;
import com.github.viblanc.gamebacklog.gamebacklog.repository.UserGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserGameServiceTests {
    @Mock
    UserGameRepository userGameRepository;
    @InjectMocks
    UserGameService userGameService;
    private UserGame userGame;

    @BeforeEach
    void setUp() {
        userGame = UserGameMother.complete().build();
        userGame.setId(new UserGameId("test_user", userGame.getGame().getId()));
    }

    @Test
    public void findById() {
        when(userGameRepository.findById(any(UserGameId.class))).thenReturn(Optional.of(userGame));
        assertThat(userGameService.findById(userGame.getId())).contains(userGame);
    }

    @Test
    public void save() {
        when(userGameRepository.save(userGame)).thenReturn(userGame);
        assertThat(userGameService.save(userGame)).isEqualTo(userGame);
    }

    @Test
    public void delete() {
        userGameService.delete(userGame);
        verify(userGameRepository, times(1)).delete(eq(userGame));
    }
}
