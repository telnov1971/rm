package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.Expiration;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;
import java.util.Optional;

@Service
public class ExpirationService extends CrudService<Expiration, Long> {
    private final ExpirationRepository expirationRepository;

    public ExpirationService(ExpirationRepository expirationRepository) {
        this.expirationRepository = expirationRepository;
    }


    @Override
    protected ExpirationRepository getRepository() {
        return expirationRepository;
    }

    public List<Expiration> findAllByDemand(Demand demand) {
        return expirationRepository.findAllByDemand(demand);
    }

    public Optional<Expiration> findById(Long id) {
        return expirationRepository.findById(id);
    }
}
