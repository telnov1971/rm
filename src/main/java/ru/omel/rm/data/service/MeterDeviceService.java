package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;

@Service
public class MeterDeviceService {
    private final MeterDeviceRepository meterDeviceRepository;

    public MeterDeviceService(MeterDeviceRepository meterDeviceRepository) {
        this.meterDeviceRepository = meterDeviceRepository;
    }
}
