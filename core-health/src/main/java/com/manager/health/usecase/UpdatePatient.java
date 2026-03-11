package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.Address;
import com.manager.shared.domain.model.Email;
import com.manager.shared.domain.model.Phone;
import com.manager.shared.domain.validation.DomainValidationException;
import com.manager.shared.domain.validation.ValidationNotification;

public class UpdatePatient {

    private final IPatientRepository repository;

    public UpdatePatient(IPatientRepository repository) {
        this.repository = repository;
    }

    public void execute(String document, RegisterPatientRequest request) {

        Patient existing = repository.findByDocument(document).orElseThrow(
                () -> new IllegalArgumentException("Paciente não encontrado!")
        );

        ValidationNotification validationNotification = new ValidationNotification();

        try {
            Email email = new Email(request.email());
            Phone phone = new Phone(request.phoneCountryCode(), request.phoneNumber());
            Address address = new Address(
                    request.street(), request.number(), request.complement(), request.neighborhood(),
                    request.city(), request.state(), request.zipCode(), request.countryCode()
            );

            existing.setEmail(email);
            existing.setPhone(phone);
            existing.setAddress(address);
            existing.setName(request.name());

            repository.update(existing);
        } catch (Exception e) {
            validationNotification.addError(e.getMessage());
            throw new DomainValidationException(validationNotification.getErrorMessage());
        }

    }
}
