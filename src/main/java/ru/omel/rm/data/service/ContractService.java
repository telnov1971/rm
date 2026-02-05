package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Contract;

import java.util.Optional;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Optional<Contract> findByStrNumber(String abNum) {
        return contractRepository.findByStrNumber(abNum);
    }

    public Optional<Contract> findByStrName(String name) {
        return contractRepository.findByStrName(name);
    }


    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    public Optional<Contract> findByExtId(Long valueOf) {
        return contractRepository.findByExtId(valueOf);
    }
}
