package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.FileStored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStoredRepository extends JpaRepository<FileStored, Long> {
    List<FileStored> findAllByDemand(Demand demand);
}