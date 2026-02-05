package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Contract;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByStrName(String name);

    Optional<Contract> findByStrNumber(String abNum);

    Optional<Contract> findByExtId(Long valueOf);
}