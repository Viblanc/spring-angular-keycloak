package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.Game;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class GameMother {
    public static Game.GameBuilder complete() {
        return Game.builder()
                .id(1077L)
                .name("Super Mario Galaxy")
                .releaseDate(LocalDate.from(Instant.ofEpochSecond(1193875200).atZone(ZoneId.of("UTC")).toLocalDate()))
                .coverUrl("https://images.igdb.com/igdb/image/upload/t_cover_big_2x/co21ro.jpg")
                .summary("""
                        A 3D platformer and first Wii entry in the Super Mario franchise, Super Mario Galaxy
                        sees Mario jump across planets and galaxies with varying items, enemies, geographies
                        and gravity mechanics in order to reach his enemy Bowser, who has attacked the Mushroom
                        Kingdom and hijacked Princess Peach's castle with her inside.
                        """)
                .platforms(List.of(PlatformMother.complete().build()));
    }
}
