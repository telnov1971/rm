package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Safety;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafetyRepository extends JpaRepository<Safety, Long> {
}