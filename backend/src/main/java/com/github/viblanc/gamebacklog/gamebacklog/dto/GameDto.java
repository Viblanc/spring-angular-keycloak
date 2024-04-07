package com.github.viblanc.gamebacklog.gamebacklog.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private Long id;
    private String name;
    private Date releaseDate;
    private String coverUrl;
    @Column(columnDefinition = "text")
    private String summary;
    private List<PlatformDto> platforms;
}
