package com.github.viblanc.gamebacklog.gamebacklog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    @Column(columnDefinition = "DATE")
    private LocalDate releaseDate;
    private String coverUrl;
    @Column(columnDefinition = "text", length = 2048)
    private String summary;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private List<Platform> platforms;
    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<UserGame> users;
}