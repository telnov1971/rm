package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Pok;

import java.util.List;
import java.util.Optional;

@Service
public class PokService {
    private final PokRepository pokRepository;

    public PokService(PokRepository pokRepository) {
        this.pokRepository = pokRepository;
    }

    public List<Pok> findAllByCeId(String ceId) {
        return pokRepository.findAllByCeId(ceId);
    }

    public Pok update(Pok pok) {
        return pokRepository.saveAndFlush(pok);
    }

    public List<Pok> updateAll(List<Pok> poks){
        return pokRepository.saveAll(poks);
    }

    public Optional<Pok> findByAbIdAndCeIdAndPdate(String ab_id, String ce_id, String pdate) {
        return pokRepository.findByAbIdAndCeIdAndPdate(ab_id, ce_id, pdate);
    }

    public void deleteAll() {
        pokRepository.deleteAll();
    }

    public long getCount() {
        return pokRepository.count();
    }

    public List<Pok> findAll() {
        return pokRepository.findAll();
    }
}
