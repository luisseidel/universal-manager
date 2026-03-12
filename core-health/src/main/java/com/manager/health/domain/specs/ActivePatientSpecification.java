package com.manager.health.domain.specs;

import com.manager.health.domain.model.Patient;
import com.manager.shared.repository.ISpecification;

public class ActivePatientSpecification implements ISpecification<Patient> {

    private final boolean active;

    public ActivePatientSpecification(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isSatisfiedBy(Patient obj) {
        return active == obj.isActive();
    }
}
