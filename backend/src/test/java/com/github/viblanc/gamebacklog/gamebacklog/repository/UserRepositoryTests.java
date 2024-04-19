package com.github.viblanc.gamebacklog.gamebacklog.repository;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserMother;
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
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager entityManager;
    private User user;

    @BeforeEach
    void setUp() {
        user = UserMother.base().build();
    }

    @Test
    void givenCreatedUser_whenFindById_thenSuccess() {
        entityManager.persist(user);
        Optional<User> retrievedUser = userRepository.findById(user.getUsername());
        assertThat(retrievedUser).contains(user);
    }

    @Test
    void givenCreatedUser_whenExistsById_thenSuccess() {
        entityManager.persist(user);
        boolean exists = userRepository.existsById(user.getUsername());
        assertThat(exists).isTrue();
    }

    @Test
    void givenUserCreated_whenSave_thenSuccess() {
        User savedUser = userRepository.save(user);
        assertThat(entityManager.find(User.class, savedUser.getUsername())).isEqualTo(user);
    }
}
