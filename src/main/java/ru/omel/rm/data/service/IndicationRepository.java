package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Indication;
import ru.omel.rm.data.entity.MeterDevice;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface IndicationRepository extends JpaRepository<Indication, Long> {
    Optional<Indication> findByIdMeterDeviceAndDate(
            MeterDevice id
            , Date date);

    List<Indication> findByIdMeterDevice(MeterDevice mt);
}