package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}