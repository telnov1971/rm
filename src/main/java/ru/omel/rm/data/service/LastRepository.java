package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Last;

import java.util.Optional;

public interface LastRepository extends JpaRepository<Last, Long> {
    Optional<Last> findById(Long id);
}