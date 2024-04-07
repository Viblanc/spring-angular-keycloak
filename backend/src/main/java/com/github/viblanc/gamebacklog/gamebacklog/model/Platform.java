package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "platforms")
public class Platform {
    @Id
    private Long id;
    private String name;
    private String alternativeName;
    private String abbreviation;
    @ManyToMany(mappedBy = "platforms")
    private List<Game> games;
}
