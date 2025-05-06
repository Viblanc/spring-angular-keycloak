package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(String username) {
        return userRepository.findById(username);
    }

    public User getUser(String username) {
        return findById(username).orElse(save(new User(username)));
    }

    public boolean existsById(String username) { return userRepository.existsById(username); }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findByUsernameStartsWith(String username) {
        return userRepository.findByUsernameStartsWith(username);
    }
}
