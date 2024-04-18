package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.Platform;
import com.github.viblanc.gamebacklog.gamebacklog.objectmother.PlatformMother;
import com.github.viblanc.gamebacklog.gamebacklog.repository.PlatformRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlatformServiceTests {
    @Mock
    PlatformRepository platformRepository;
    @InjectMocks
    PlatformService platformService;
    private Platform platform;

    @BeforeEach
    void setUp() {
        platform = PlatformMother.complete().build();
    }

    @Test
    public void isEmpty() {
        when(platformRepository.count()).thenReturn(0L);
        assertThat(platformService.isEmpty()).isTrue();
        when(platformRepository.count()).thenReturn(1L);
        assertThat(platformService.isEmpty()).isFalse();
    }

    @Test
    public void findById() {
        when(platformRepository.findById(platform.getId())).thenReturn(Optional.of(platform));
        assertThat(platformService.findById(platform.getId())).contains(platform);
    }

    @Test
    public void findAllById() {
        Iterable<Long> ids = List.of(platform.getId());
        List<Platform> platforms = List.of(platform);
        when(platformRepository.findAllById(any())).thenReturn(platforms);
        assertThat(platformService.findAllById(ids)).isEqualTo(platforms);
    }

    @Test
    public void save() {
        when(platformRepository.save(platform)).thenReturn(platform);
        assertThat(platformService.save(platform)).isEqualTo(platform);
    }

    @Test
    public void saveAll() {
        List<Platform> platforms = List.of(platform);
        when(platformRepository.saveAll(any())).thenReturn(platforms);
        assertThat(platformService.saveAll(platforms)).isEqualTo(platforms);
    }

    @Test
    public void delete() {
        platformService.delete(platform);
        verify(platformRepository, times(1)).delete(eq(platform));
    }
}
