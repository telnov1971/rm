package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Last;

import java.util.Optional;

@Service
public class LastService {
    private final LastRepository lastRepository;

    public LastService(LastRepository lastRepository) {
        this.lastRepository = lastRepository;
    }

    public Optional<Last> getLast(Long id) {
        return lastRepository.findById(id);
    }

    public Last update(Last last) {
        return lastRepository.save(last);
    }
}
