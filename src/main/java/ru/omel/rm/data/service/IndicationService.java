package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Indication;
import ru.omel.rm.data.entity.MeterDevice;
import ru.omel.rm.data.entity.Pok;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IndicationService {
    private final IndicationRepository indicationRepository;

    public IndicationService(IndicationRepository indicationRepository) {
        this.indicationRepository = indicationRepository;
    }

    public Optional<Indication> findByIdMeterDeviceAndDate(
            MeterDevice id, Date date) {
        return indicationRepository
                .findByIdMeterDeviceAndDate(id, date);
    }

    public void save(Indication ind) {
        indicationRepository.save(ind);
    }

    public List<Indication> findByIdMeterDevice(MeterDevice mt) {
        return indicationRepository.findByIdMeterDevice(mt);
    }
}
