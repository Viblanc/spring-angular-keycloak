package com.github.viblanc.gamebacklog.gamebacklog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private String coverUrl;
    @Column(columnDefinition = "text")
    private String summary;
    private List<PlatformDto> platforms;

    @JsonProperty("first_release_date")
    private void convertReleaseDate(Long timestamp) {
        this.releaseDate = Instant.ofEpochSecond(timestamp).atZone(ZoneId.of("UTC")).toLocalDate();
    }

    @JsonProperty("cover")
    private void unpackNested(Map<String,String> cover) {
        this.coverUrl = cover.get("url").replace("thumb", "cover_big_2x");
    }
}
