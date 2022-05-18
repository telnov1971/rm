package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByDemand(Demand demand);
}