package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.General;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralRepository extends JpaRepository<General, Long> {
    List<General> findAllByDemand(Demand demand);
}