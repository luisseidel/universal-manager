package com.manager.health.domain.specs;

import com.manager.health.domain.model.Patient;
import com.manager.shared.repository.ISpecification;

public class PatientNameLikeSpecification implements ISpecification<Patient> {

    private final String name;

    public PatientNameLikeSpecification(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Patient obj) {
        if (name != null && name.trim() != ""
            && obj != null && obj.getName() != null
                && obj.getName().trim() != ""
        ) {
            return name.equals(obj.getName().toLowerCase());
        }
        return false;
    }
}
