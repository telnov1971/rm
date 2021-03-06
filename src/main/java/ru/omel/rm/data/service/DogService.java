package ru.omel.rm.data.service;

import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.Dog;

import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Optional<Dog> findByAbNum(String abNum) {
        return dogRepository.findByAbNum(abNum);
    }

    public Dog update(Dog dog) {
        return dogRepository.saveAndFlush(dog);
    }

    public void deleteAll() {
        dogRepository.deleteAll();
    }

    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    public List<Dog> updateAll(List<Dog> dogs) {
        return dogRepository.saveAll(dogs);
    }

    public long getCount() {
        return dogRepository.count();
    }
}
