package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
//    Contract findByNum(String abNum);
}