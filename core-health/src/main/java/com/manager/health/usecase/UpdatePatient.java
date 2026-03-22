package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.UpdatePatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.mapper.PatientMapper;
import com.manager.shared.domain.validation.DomainValidationException;
import com.manager.shared.domain.validation.ValidationNotification;

public class UpdatePatient {

    private final IPatientRepository repository;
    private final PatientMapper mapper;

    public
    UpdatePatient(IPatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void execute(String document, UpdatePatientRequest request) {

        Patient existing = repository.findByDocument(document).orElseThrow(
                () -> new IllegalArgumentException("Paciente não encontrado!")
        );

        ValidationNotification validationNotification = new ValidationNotification();

        try {
            mapper.updateEntity(existing, request);
            repository.update(existing);
        } catch (Exception e) {
            validationNotification.addError(e.getMessage());
            throw new DomainValidationException(validationNotification.getErrorMessage());
        }

    }
}
