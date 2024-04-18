package com.github.viblanc.gamebacklog.gamebacklog.objectmother;

import com.github.viblanc.gamebacklog.gamebacklog.model.Platform;

public class PlatformMother {
    public static Platform.PlatformBuilder complete() {
        return Platform.builder()
                .id(5L)
                .name("Wii")
                .alternativeName("Revolution")
                .abbreviation("Wii");
    }
}
