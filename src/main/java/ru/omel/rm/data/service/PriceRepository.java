package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}