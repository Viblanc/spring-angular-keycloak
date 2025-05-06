package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserMother;
import com.github.viblanc.gamebacklog.gamebacklog.repository.UserRepository;
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
public class UserServiceTests {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = UserMother.base().build();
    }

    @Test
    public void findUserById() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertThat(userService.findById(user.getUsername())).contains(user);
    }

    @Test
    public void getUserById() {
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));
        assertThat(user).isEqualTo(userService.getUser(user.getUsername()));
    }

    @Test
    public void existsById() {
        when(userRepository.existsById(user.getUsername())).thenReturn(true);
        assertThat(userService.existsById(user.getUsername())).isTrue();
        when(userRepository.existsById(user.getUsername())).thenReturn(false);
        assertThat(userService.existsById(user.getUsername())).isFalse();
    }

    @Test
    public void saveUser() {
        when(userRepository.save(any(User.class))).thenReturn(UserMother.base().build());
        assertThat(user).isEqualTo(userService.save(user));
    }
}
