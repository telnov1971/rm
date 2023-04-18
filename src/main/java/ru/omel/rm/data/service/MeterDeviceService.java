package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.MeterDevice;

import java.util.Optional;

@Service
public class MeterDeviceService {
    private final MeterDeviceRepository meterDeviceRepository;
    public MeterDeviceService(MeterDeviceRepository meterDeviceRepository) {
        this.meterDeviceRepository = meterDeviceRepository;
    }
    public Optional<MeterDevice> findByExtId(Long extId) {
        return meterDeviceRepository.findById(extId);
    }
    public void save(MeterDevice mt) {
        meterDeviceRepository.save(mt);
    }
}
