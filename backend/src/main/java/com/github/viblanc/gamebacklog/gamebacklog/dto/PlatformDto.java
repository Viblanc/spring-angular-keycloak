package com.github.viblanc.gamebacklog.gamebacklog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformDto {
    private Long id;
    private String name;
    @JsonProperty("alternative_name")
    private String alternativeName;
    private String abbreviation;
}
