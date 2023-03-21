package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Indication;

public interface IndicationRepository extends JpaRepository<Indication, Long> {
}