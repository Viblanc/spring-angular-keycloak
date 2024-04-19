package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.GameMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainerConfig.class)
public class GameRepositoryTests {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TestEntityManager entityManager;
    private Game game;

    @BeforeEach
    void setUp() {
        game = GameMother.complete().build();
    }

    @Test
    void givenGameCreated_whenFindById_thenSuccess() {
        entityManager.persist(game);
        Optional<Game> retrievedGame = gameRepository.findById(game.getId());
        assertThat(retrievedGame).contains(game);
    }

    @Test
    void givenGameCreated_whenExistsById_thenSuccess() {
        entityManager.persist(game);
        boolean exists = gameRepository.existsById(game.getId());
        assertThat(exists).isTrue();
    }

    @Test
    void givenNewGame_whenSave_thenSuccess() {
        Game savedGame = gameRepository.save(game);
        assertThat(entityManager.find(Game.class, savedGame.getId())).isEqualTo(game);
    }

    @Test
    void givenGameCreated_whenDelete_thenSuccess() {
        entityManager.persist(game);
        gameRepository.delete(game);
        assertThat(entityManager.find(Game.class, game.getId())).isNull();
    }
}
