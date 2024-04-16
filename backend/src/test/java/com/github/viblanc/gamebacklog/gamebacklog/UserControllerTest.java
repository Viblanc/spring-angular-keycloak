package com.github.viblanc.gamebacklog.gamebacklog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.viblanc.gamebacklog.gamebacklog.configuration.TestContainersConfiguration;
import com.github.viblanc.gamebacklog.gamebacklog.service.IGDBApiService;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainersConfiguration.class)
@Slf4j
public class UserControllerTest {
    static final String CLIENT_ID = "gamebacklog-app";
    static final String USERNAME = "test_user";
    static final String PASSWORD = "test_password";
    @LocalServerPort
    private int port;

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    @MockBean
    private IGDBApiService igdbApiService;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void givenAuthenticatedUser_whenGetMe_shouldReturnMyInfo() {
        given().header("Authorization", "Bearer " + getToken())
                .when()
                .get("/api/users/me")
                .then()
                .body("username", equalTo("test_user"));
    }

    private String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", Collections.singletonList(OAuth2Constants.PASSWORD));
        map.put("client_id", Collections.singletonList(CLIENT_ID));
        map.put("username", Collections.singletonList(USERNAME));
        map.put("password", Collections.singletonList(PASSWORD));

        String authServerUrl = oAuth2ResourceServerProperties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";
        var request = new HttpEntity<>(map, httpHeaders);
        KeycloakToken token = restTemplate.postForObject(
                authServerUrl,
                request,
                KeycloakToken.class
        );
        assert token != null;
        return token.accessToken;
    }

    record KeycloakToken(@JsonProperty("access_token") String accessToken) {}
}
