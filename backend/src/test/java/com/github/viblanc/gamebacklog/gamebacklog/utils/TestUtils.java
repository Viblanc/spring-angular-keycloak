package com.github.viblanc.gamebacklog.gamebacklog.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.keycloak.OAuth2Constants;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TestUtils {
    public static RequestSpecification getRequestSpec(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        String token = getToken(oAuth2ResourceServerProperties);
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    private static String getToken(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        final String CLIENT_ID = "gamebacklog-app";
        final String USERNAME = "test_user";
        final String PASSWORD = "test_password";
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
        return token.accessToken();
    }

    record KeycloakToken(@JsonProperty("access_token") String accessToken) {}
}
