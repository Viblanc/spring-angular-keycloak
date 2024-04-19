package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserGameMother;
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
public class UserGameRepositoryTests {
    @Autowired
    UserGameRepository userGameRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager entityManager;
    private UserGame userGame;

    @BeforeEach
    void setUp() {
        userGame = UserGameMother.complete().build();
    }

    @Test
    void givenUserGameCreated_whenFindById_thenSuccess() {
        entityManager.persist(userGame);
        Optional<UserGame> retrievedUserGame = userGameRepository.findById(userGame.getId());
        assertThat(retrievedUserGame).contains(userGame);
    }

    @Test
    void givenNewUserGame_whenSave_thenSuccess() {
        userRepository.save(userGame.getUser());
        UserGame savedUserGame = userGameRepository.save(userGame);
        assertThat(entityManager.find(UserGame.class, savedUserGame.getId())).isEqualTo(userGame);
    }

    @Test
    void givenUserGameCreated_whenDelete_thenSuccess() {
        entityManager.persist(userGame);
        userGameRepository.delete(userGame);
        assertThat(entityManager.find(UserGame.class, userGame.getId())).isNull();
    }
}
