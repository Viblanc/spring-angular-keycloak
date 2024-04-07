package com.github.viblanc.gamebacklog.gamebacklog.dto;

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
    private String alternativeName;
    private String abbreviation;
}
