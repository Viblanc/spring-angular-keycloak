package com.github.viblanc.gamebacklog.gamebacklog.service;

import com.github.viblanc.gamebacklog.gamebacklog.model.Platform;
import com.github.viblanc.gamebacklog.gamebacklog.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;

    public boolean isEmpty() {
        return platformRepository.count() == 0;
    }

    public Optional<Platform> findById(Long id) {
        return platformRepository.findById(id);
    }

    public List<Platform> findAllById(Iterable<Long> ids) {
        return platformRepository.findAllById(ids);
    }

    public Platform save(Platform platform) {
        return platformRepository.save(platform);
    }

    public List<Platform> saveAll(Iterable<Platform> platforms) {
        return platformRepository.saveAll(platforms);
    }

    public void delete(Platform platform) {
        platformRepository.delete(platform);
    }
}
