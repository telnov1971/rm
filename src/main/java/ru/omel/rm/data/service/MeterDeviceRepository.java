package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Contract;
import ru.omel.rm.data.entity.MeterDevice;

import java.util.List;
import java.util.Optional;

public interface MeterDeviceRepository extends JpaRepository<MeterDevice, Long> {
    Optional<MeterDevice> findByExtId(Long extId);

    List<MeterDevice> findByContract(Contract contract);
}