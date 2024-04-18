package com.github.viblanc.gamebacklog.gamebacklog.controller;

import com.github.viblanc.gamebacklog.gamebacklog.utils.TestUtils;
import com.github.viblanc.gamebacklog.gamebacklog.configuration.KeycloakContainerConfig;
import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({PostgresContainerConfig.class, KeycloakContainerConfig.class})
public class UserControllerTests {
    @LocalServerPort
    private int port;
    private RequestSpecification requestSpec;

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        requestSpec = TestUtils.getRequestSpec(oAuth2ResourceServerProperties);
    }

    @Test
    void givenAuthenticatedUser_whenGetMe_shouldReturnMyInfo() {
        given().spec(requestSpec)
                .when()
                .get("/api/users/me")
                .then()
                .body("username", equalTo("test_user"));
    }

    @Test
    void givenAuthenticatedUser_whenGetGames_shouldReturnGames() {
        given().spec(requestSpec)
                .when()
                .get("/api/games")
                .then()
                .body(is(equalTo("[]")));
    }

}
