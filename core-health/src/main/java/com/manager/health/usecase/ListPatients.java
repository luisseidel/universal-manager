package com.manager.health.usecase;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.repository.IPatientRepository;

import java.util.List;

public class ListPatients {

    private final IPatientRepository repository;

    public ListPatients(IPatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponse> execute() {
        return repository.findAll().stream()
                .map(p -> new PatientResponse(
                        p.getName(),
                        p.getDocument().getFormatted(),
                        p.getPhone().getFormatted(),
                        p.getEmail().getValue(),
                        p.getAddress().getFullAddress()
                ))
                .toList();
    }
}