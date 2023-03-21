package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Contract;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

//    public Contract findByNum(String abNum) {
//        return contractRepository.findByNum(abNum);
//    }

//    public void save(Contract contract) {
//        contractRepository.save(contract);
//    }
}
