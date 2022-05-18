package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.Expiration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpirationRepository extends JpaRepository<Expiration, Long> {
    List<Expiration> findAllByDemand(Demand demand);
}