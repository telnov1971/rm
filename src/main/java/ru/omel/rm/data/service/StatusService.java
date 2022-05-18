package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Status;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService extends CrudService<Status, Long> {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    protected StatusRepository getRepository() {
        return statusRepository;
    }

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Optional<Status> findById(long l) {
        return statusRepository.findById(l);
    }
}
