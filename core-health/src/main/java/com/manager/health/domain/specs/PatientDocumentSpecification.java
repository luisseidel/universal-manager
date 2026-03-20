package com.manager.health.domain.specs;

import com.manager.health.domain.model.Patient;
import com.manager.shared.repository.ISpecification;

public class PatientDocumentSpecification implements ISpecification<Patient> {

    private final String documentNumber;

    public PatientDocumentSpecification(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public boolean isSatisfiedBy(Patient obj) {
        if (documentNumber != null && documentNumber.trim() != ""
                && obj != null && obj.getDocument() != null
                && obj.getDocument().getRawValue() != ""
        ) {
            return documentNumber.equals(obj.getDocument().getRawValue());
        }
        return false;
    }
}
