package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.github.viblanc.gamebacklog.gamebacklog.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IGDBApiService {
    private final String clientId;
    private final String clientSecret;
    private final TwitchAuthenticator authenticator;
    private String accessToken;

    public IGDBApiService(@Value("${app.igdb.clientId}") String clientId,
                          @Value("${app.igdb.clientSecret}") String clientSecret) {
        this.authenticator = TwitchAuthenticator.INSTANCE;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessToken = Objects.requireNonNull(authenticator.requestTwitchToken(this.clientId, this.clientSecret)).getAccess_token();
    }

    @Retryable(retryFor = RequestException.class, maxAttempts = 2)
    public Optional<List<Game>> searchGames(String query) {
        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials(clientId, accessToken);
        APICalypse apicalypse =
                new APICalypse().fields("id,name,first_release_date,cover.url,summary").search(query).limit(12);

        try {
            return Optional.of(ProtoRequestKt.games(wrapper, apicalypse)
                    .stream()
                    .map(game -> {
                        Date releaseDate = Date.from(Instant.ofEpochSecond(
                                game.getFirstReleaseDate().getSeconds(),
                                game.getFirstReleaseDate().getNanos()));
                        String coverUrl = "https:" + game.getCover().getUrl().replace("thumb", "cover_big_2x");
                        return Game.builder()
                                .id(game.getId())
                                .name(game.getName())
                                .releaseDate(releaseDate)
                                .coverUrl(coverUrl)
                                .summary(game.getSummary())
                                .build();
                    })
                    .toList());
        } catch (RequestException e) {
            accessToken = Objects.requireNonNull(authenticator.requestTwitchToken(clientId, clientSecret)).getAccess_token();
            log.error("Request to send search query {} failed: {}", query, e.getMessage());
        }

        return Optional.empty();
    }
}
