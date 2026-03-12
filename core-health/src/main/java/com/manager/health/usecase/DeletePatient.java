package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientIRepository;

import java.util.Optional;

public class DeletePatient {

    private final IPatientIRepository repository;

    public DeletePatient(IPatientIRepository repository) {
        this.repository = repository;
    }

    public void execute(String document) {
        Optional<Patient> patient = repository.findByDocument(document);

        if (patient.isPresent()) {
            this.repository.delete(patient.get().getId());
            System.out.println("Paciente excluído! " + patient.get().getName());
        } else {
            throw new IllegalArgumentException("Paciente não encontrado para exclusão");
        }

    }
}
