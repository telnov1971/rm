package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Contract;
import ru.omel.rm.data.entity.MeterDevice;

import java.util.List;
import java.util.Optional;

@Service
public class MeterDeviceService {
    private final MeterDeviceRepository meterDeviceRepository;
    public MeterDeviceService(MeterDeviceRepository meterDeviceRepository) {
        this.meterDeviceRepository = meterDeviceRepository;
    }
    public Optional<MeterDevice> findByExtId(Long extid) {
        return meterDeviceRepository.findByExtId(extid);
    }
    public void save(MeterDevice mt) {
        meterDeviceRepository.save(mt);
    }

    public List<MeterDevice> findByContract(Contract contract) {
        return meterDeviceRepository.findByContract(contract);
    }
}
