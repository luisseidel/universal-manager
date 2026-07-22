package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.events.IEventPublisher;

import java.util.Optional;

public class DeletePatient {

    private final IPatientRepository repository;
    private final IEventPublisher eventPublisher;

    public DeletePatient(IPatientRepository repository, IEventPublisher publisher) {
        this.repository = repository;
        this.eventPublisher = publisher;
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
