package ru.omel.rm.data.service;

import ru.omel.rm.data.entity.Plan;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class PlanService extends CrudService<Plan, Long> {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan findById(long l) {
        return planRepository.findById(l).get();
    }

    @Override
    protected PlanRepository getRepository() {
        return planRepository;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }
}
