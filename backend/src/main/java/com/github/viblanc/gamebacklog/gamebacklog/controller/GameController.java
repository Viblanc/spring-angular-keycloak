package com.github.viblanc.gamebacklog.gamebacklog.controller;

import com.github.viblanc.gamebacklog.gamebacklog.dto.GameDto;
import com.github.viblanc.gamebacklog.gamebacklog.dto.UserGameDto;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.GameMapper;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.UserMapper;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;
import com.github.viblanc.gamebacklog.gamebacklog.repository.UserGameRepository;
import com.github.viblanc.gamebacklog.gamebacklog.service.GameService;
import com.github.viblanc.gamebacklog.gamebacklog.service.IGDBApiService;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final UserService userService;
    private final GameService gameService;
    private final UserGameRepository userGameRepository;
    private final IGDBApiService igdbApiService;

    @GetMapping
    public ResponseEntity<List<UserGameDto>> findGames(Principal principal) {
        String username = principal.getName();
        User user = userService.getUser(username);
        return ResponseEntity.ok(UserMapper.toDto(user).getGames());
    }

    @GetMapping("/search")
    public ResponseEntity<List<GameDto>> searchGames(Principal principal, @RequestParam String query) {
        String username = principal.getName();
        User user = userService.getUser(username);
        Optional<List<GameDto>> games = igdbApiService.searchGames(user, query);
        return games.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());
    }

    @PostMapping("/add")
    public ResponseEntity<List<UserGameDto>> addGame(Principal principal, @Valid @RequestBody GameDto gameDto) {
        String username = principal.getName();
        User currentUser = userService.getUser(username);
        Game game = GameMapper.fromDto(gameDto);
        if (!gameService.existsById(gameDto.getId()))
            gameService.save(game);
        UserGame userGame = UserGame.builder()
                .id(new UserGameId(username, gameDto.getId()))
                .user(currentUser)
                .game(game)
                .completed(false)
                .favourite(false)
                .rating(-1)
                .build();
        userGameRepository.save(userGame);
        return ResponseEntity.ok(UserMapper.toDto(currentUser).getGames());
    }
}
