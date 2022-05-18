package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Pu;

import java.util.List;
import java.util.Optional;

public interface PuRepository extends JpaRepository<Pu, Long> {
    List<Pu> findAllByAbId(String abId);

    Optional<Object> findAllByCeId(String ceId);
}