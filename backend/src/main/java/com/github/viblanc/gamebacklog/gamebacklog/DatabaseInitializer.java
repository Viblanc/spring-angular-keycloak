package com.github.viblanc.gamebacklog.gamebacklog;

import com.github.viblanc.gamebacklog.gamebacklog.service.IGDBApiService;
import com.github.viblanc.gamebacklog.gamebacklog.service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final PlatformService platformService;
    private final IGDBApiService igdbApiService;

    @Override
    public void run(String... args) {
        if (platformService.isEmpty())
            platformService.saveAll(igdbApiService.findPlatforms());
    }
}
