package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.github.viblanc.gamebacklog.gamebacklog.dto.GameDto;
import com.github.viblanc.gamebacklog.gamebacklog.mapper.GameMapper;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import com.github.viblanc.gamebacklog.gamebacklog.model.Platform;
import com.github.viblanc.gamebacklog.gamebacklog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IGDBApiService {
    private final String clientId;
    private final String clientSecret;
    private final TwitchAuthenticator authenticator;
    private final UserService userService;

    public IGDBApiService(@Value("${app.igdb.clientId}") String clientId,
                          @Value("${app.igdb.clientSecret}") String clientSecret,
                          UserService userService) {
        this.authenticator = TwitchAuthenticator.INSTANCE;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userService = userService;
    }

    @Retryable(retryFor = RequestException.class, maxAttempts = 2)
    public Optional<List<GameDto>> searchGames(User currentUser, String query) {
        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        String accessToken = Optional.ofNullable(currentUser.getIgdbAccessToken())
                        .orElseGet(() -> {
                            currentUser.setIgdbAccessToken(Objects.requireNonNull(
                                    authenticator.requestTwitchToken(clientId, clientSecret)).getAccess_token());
                            userService.save(currentUser);
                            return currentUser.getIgdbAccessToken();
                        });
        wrapper.setCredentials(clientId, accessToken);
        APICalypse apicalypse =
                new APICalypse().fields("id,name,first_release_date,cover.url,summary,platforms.*;").search(query).limit(12);

        try {
            return Optional.of(ProtoRequestKt.games(wrapper, apicalypse)
                    .stream()
                    .map(game -> {
                        LocalDate releaseDate =
                                Instant.ofEpochSecond(game.getFirstReleaseDate().getSeconds())
                                        .atZone(ZoneId.of("UTC"))
                                        .toLocalDate();
                        String coverUrl = "https:" + game.getCover().getUrl().replace("thumb", "cover_big_2x");
                        List<Platform> platforms = game.getPlatformsList().stream()
                                .map(platform -> Platform.builder()
                                        .id(platform.getId())
                                        .name(platform.getName())
                                        .alternativeName(platform.getAlternativeName())
                                        .abbreviation(platform.getAbbreviation())
                                        .build())
                                .toList();
                        return (GameMapper.toDto(Game.builder()
                                .id(game.getId())
                                .name(game.getName())
                                .releaseDate(releaseDate)
                                .coverUrl(coverUrl)
                                .summary(game.getSummary())
                                .platforms(platforms)
                                .build()));
                    })
                    .toList());
        } catch (RequestException e) {
            accessToken = Objects.requireNonNull(authenticator.requestTwitchToken(clientId, clientSecret)).getAccess_token();
            currentUser.setIgdbAccessToken(accessToken);
            userService.save(currentUser);
            wrapper.setCredentials(clientId, accessToken);
            log.error("Request to send search query {} failed: {}", query, e.getStatusCode());
        }

        return Optional.empty();
    }
}
