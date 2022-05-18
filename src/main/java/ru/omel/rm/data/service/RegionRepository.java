package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}