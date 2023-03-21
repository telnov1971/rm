package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;

@Service
public class IndicationService {
    private final IndicationRepository indicationRepository;

    public IndicationService(IndicationRepository indicationRepository) {
        this.indicationRepository = indicationRepository;
    }
}
