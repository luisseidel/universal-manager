package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.repository.IPatientIRepository;

import java.util.Optional;

public class SearchPatient {

    private final IPatientIRepository repository;

    public SearchPatient(IPatientIRepository repository) {
        this.repository = repository;
    }

    public PatientResponse execute(String document) {
        Optional<Patient> p = repository.findByDocument(document);

        if (p.isPresent()) {
            return new PatientResponse(
                    p.get().getName(),
                    p.get().getDocument().getFormatted(),
                    p.get().getPhone().getFormatted(),
                    p.get().getEmail().getValue(),
                    p.get().getAddress().getFullAddress()
            );
        }
        return null;
    }
}
