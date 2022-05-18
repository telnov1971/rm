package ru.omel.rm.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.omel.rm.data.entity.Dog;

import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByAbNum(String abNum);
}