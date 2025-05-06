package com.github.viblanc.gamebacklog.gamebacklog.controller;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import com.github.viblanc.gamebacklog.gamebacklog.dto.GameDto;
import com.github.viblanc.gamebacklog.gamebacklog.dto.UserGameDto;
import com.github.viblanc.gamebacklog.gamebacklog.exception.SearchRequestException;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.GameMapper;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.UserGameMapper;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGame;
import com.github.viblanc.gamebacklog.gamebacklog.model.UserGameId;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.GameMother;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserGameMother;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.UserMother;
import com.github.viblanc.gamebacklog.gamebacklog.service.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresContainerConfig.class)
public class GameControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private IGDBApiService igdbApiService;
    @MockBean
    private GameService gameService;
    @MockBean
    private UserGameService userGameService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
        RestAssuredMockMvc.basePath = "/api/games";
    }

    @Test
    public void whenGetGames_RespondWithGames() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));

        when(userService.getUser(user.getUsername())).thenReturn(user);

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .when()
                .get("/" + user.getUsername())
                .then()
                .status(HttpStatus.OK)
                .body("size()", equalTo(1))
                .body("[0].game.id", equalTo(userGame.getGame().getId().intValue()))
                .body("[0].game.name", equalTo(userGame.getGame().getName()))
                .body("[0].game.coverUrl", equalTo(userGame.getGame().getCoverUrl()))
                .body("[0].game.summary", equalTo(userGame.getGame().getSummary()));
    }

    @Test
    public void whenSearchForGames_ReturnListOfGames() {
        User user = UserMother.base().build();
        Game game = GameMother.complete().build();
        GameDto gameDto = GameMapper.toDto(game);
        String query = game.getName();

        when(userService.getUser(user.getUsername())).thenReturn(user);
        when(igdbApiService.searchGames(eq(user), anyString())).thenReturn(Flux.just(gameDto));

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .param("query", query)
                .when()
                .get("/search")
                .then()
                .status(HttpStatus.OK)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(game.getId().intValue()))
                .body("[0].name", equalTo(game.getName()))
                .body("[0].coverUrl", equalTo(game.getCoverUrl()))
                .body("[0].summary", equalTo(game.getSummary()));
    }

    @Test
    public void whenSearchForGames_ifIGDBFails_ReturnGatewayError() {
        User user = UserMother.base().build();

        when(userService.getUser(user.getUsername())).thenReturn(user);
        when(igdbApiService.searchGames(eq(user), anyString())).thenReturn(Flux.error(new SearchRequestException(new Throwable())));

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .param("query", "test")
                .when()
                .get("/search")
                .then()
                .status(HttpStatus.BAD_GATEWAY);
    }

    @Test
    public void whenAddGame_ReturnListOfUserGames() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));
        Game game = userGame.getGame();
        GameDto gameDto = GameMapper.toDto(game);

        when(userService.getUser(user.getUsername())).thenReturn(user);
        when(gameService.save(game)).thenReturn(game);
        when(userGameService.save(userGame)).thenReturn(userGame);

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .body(gameDto)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/add")
                .then()
                .status(HttpStatus.OK)
                .body("size()", equalTo(1))
                .body("[0].game.id", equalTo(game.getId().intValue()))
                .body("[0].game.name", equalTo(game.getName()))
                .body("[0].game.coverUrl", equalTo(game.getCoverUrl()))
                .body("[0].game.summary", equalTo(game.getSummary()));
    }

    @Test
    public void whenUpdateGame_ReturnUpdatedGame() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));
        UserGameDto userGameDto = UserGameMapper.toDto(userGame);
        userGameDto.setCompleted(true);
        userGameDto.setRating(9);
        Long gameId = userGame.getGame().getId();

        when(userGameService.findById(any(UserGameId.class))).thenReturn(Optional.of(userGame));
        when(userGameService.save(userGame)).thenReturn(userGame);

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .pathParam("id", gameId)
                .body(userGameDto)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put("/{id}")
                .then()
                .status(HttpStatus.OK)
                .body("completed", equalTo(true))
                .body("favourite", equalTo(false))
                .body("rating", equalTo(9));
    }

    @Test
    public void whenUpdateNonExistingGame_ReturnNotFound() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));
        UserGameDto userGameDto = UserGameMapper.toDto(userGame);

        when(userGameService.findById(any(UserGameId.class))).thenReturn(Optional.empty());

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .pathParam("id", userGame.getGame().getId())
                .body(userGameDto)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put("/{id}")
                .then()
                .status(HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenDeleteGame_ReturnNoContent() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));

        when(userService.getUser(user.getUsername())).thenReturn(user);
        when(userGameService.findById(any(UserGameId.class))).thenReturn(Optional.of(userGame));

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .pathParam("id", userGame.getGame().getId())
                .when()
                .delete("/{id}")
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    public void whenDeleteNonExistingGame_ReturnNotFound() {
        UserGame userGame = UserGameMother.complete().build();
        User user = userGame.getUser();
        user.setGames(List.of(userGame));

        when(userService.getUser(user.getUsername())).thenReturn(user);
        when(userGameService.findById(any(UserGameId.class))).thenReturn(Optional.empty());

        given()
                .auth().with(jwt().jwt(jwt -> jwt.subject(user.getUsername()).build()))
                .pathParam("id", userGame.getGame().getId())
                .when()
                .delete("/{id}")
                .then()
                .status(HttpStatus.NOT_FOUND);
    }
}
