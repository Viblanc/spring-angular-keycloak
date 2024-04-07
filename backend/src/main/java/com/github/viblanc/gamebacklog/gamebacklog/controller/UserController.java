package com.github.viblanc.gamebacklog.gamebacklog.controller;

import com.github.viblanc.gamebacklog.gamebacklog.dto.UserDto;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.UserMapper;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String username = principal.getName();
        if (!userService.existsById(username)) {
            userService.save(new User(username));
        }
        User currentUser = userService.getUser(username);
        return ResponseEntity.ok(UserMapper.toDto(currentUser));
    }
}
