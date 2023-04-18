package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Pu;

import java.util.List;
import java.util.Optional;

@Service
public class PuService {
    private final PuRepository puRepository;

    public PuService(PuRepository puRepository) {
        this.puRepository = puRepository;
    }

    public List<Pu> findAllByAbId(String abId) {
        return puRepository.findAllByAbId(abId);
    }

    public Optional<Object> findAllByCeId(String ceId) {
        return puRepository.findAllByCeId(ceId);
    }

    public Pu update(Pu pu) {
        return puRepository.saveAndFlush(pu);
    }

    public void deleteAll() {
        puRepository.deleteAll();
    }

    public List<Pu> updateAll(List<Pu> pus) {
        return puRepository.saveAll(pus);
    }

    public long getCount() {
        return puRepository.count();
    }

    public List<Pu> findAll() {
        return puRepository.findAll();
    }
}
