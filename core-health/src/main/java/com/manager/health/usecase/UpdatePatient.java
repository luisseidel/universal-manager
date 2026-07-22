package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.model.UpdatePatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.IMapper;
import com.manager.shared.domain.validation.DomainValidationException;
import com.manager.shared.domain.validation.ValidationNotification;
import com.manager.shared.events.IEventPublisher;

public class UpdatePatient {

    private final IPatientRepository repository;
    private final IMapper<Patient, PatientResponse, RegisterPatientRequest, UpdatePatientRequest> mapper;
    private final IEventPublisher eventPublisher;

    public UpdatePatient(IPatientRepository repository, IMapper mapper, IEventPublisher publisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = publisher;
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
