package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Send;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendRepository extends JpaRepository<Send, Long> {
}