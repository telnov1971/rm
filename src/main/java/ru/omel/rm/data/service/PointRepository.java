package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByDemand(Demand demand);
}