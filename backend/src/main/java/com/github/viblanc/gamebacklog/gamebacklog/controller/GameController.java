package com.github.viblanc.gamebacklog.gamebacklog.controller;
import com.github.viblanc.gamebacklog.gamebacklog.service.IGDBApiService;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final IGDBApiService igdbApiService;

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames(@RequestParam String query) {
        Optional<List<Game>> games = igdbApiService.searchGames(query);
        return games.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());
    }

}
