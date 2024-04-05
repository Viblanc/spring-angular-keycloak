package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "games")
public class Game {
    @Id
    private Long id;
    private String name;
    private Date releaseDate;
    private String coverUrl;
    private String summary;
}