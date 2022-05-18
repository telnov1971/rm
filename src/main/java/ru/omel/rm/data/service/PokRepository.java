package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Pok;

import java.util.List;
import java.util.Optional;

public interface PokRepository extends JpaRepository<Pok, Long> {
    List<Pok> findAllByCeId(String ceId);

    Optional<Pok> findByAbIdAndCeIdAndPdate(String ab_id, String ce_id, String pdate);
}