package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.MeterDevice;

public interface MeterDeviceRepository extends JpaRepository<MeterDevice, Long> {
}