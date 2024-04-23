package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.viblanc.gamebacklog.gamebacklog.dto.GameDto;
import com.github.viblanc.gamebacklog.gamebacklog.exception.SearchRequestException;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
public class IGDBApiService {
    private final String clientId;
    private final String clientSecret;
    private final UserService userService;
    private final WebClient webClient;

    public IGDBApiService(@Value("${app.igdb.clientId}") String clientId,
                          @Value("${app.igdb.clientSecret}") String clientSecret,
                          UserService userService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userService = userService;
        this.webClient = WebClient.create();
    }

    private Mono<String> requestNewAccessToken(User user) {
        log.info("Requesting new access token for user {}", user.getUsername());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "client_credentials");
        return webClient.post()
                .uri("https://id.twitch.tv/oauth2/token")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(AccessToken.class)
                .doOnNext(token -> {
                    user.setIgdbAccessToken(token.accessToken());
                    userService.save(user);
                })
                .map(AccessToken::accessToken);
    }

    public Flux<GameDto> searchGames(User user, String query) {
        return Mono.justOrEmpty(Optional.ofNullable(user.getIgdbAccessToken()))
                .switchIfEmpty(Mono.defer(() -> requestNewAccessToken(user)))
                .flatMapMany(token -> {
                    String body = String.format("fields id,name,first_release_date,cover.url,summary,platforms.*;" +
                            "search \"%s\";limit 12;", query);
                    return webClient.post()
                            .uri("https://api.igdb.com/v4/games")
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Client-ID", clientId)
                            .header("Authorization", "Bearer " + token)
                            .bodyValue(body)
                            .retrieve()
                            .bodyToFlux(GameDto.class)
                            .onErrorResume(e -> {
                                log.warn("Request failed; Trying with new access token");
                                user.setIgdbAccessToken(null);
                                userService.save(user);
                                return Mono.error(e);
                            })
                            .retryWhen(Retry.backoff(3, Duration.of(2, ChronoUnit.SECONDS))
                                    .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> new SearchRequestException(retrySignal.failure()))));
                });
    }

    private record AccessToken(
            @JsonProperty("access_token")
            String accessToken
    ) {}
}
