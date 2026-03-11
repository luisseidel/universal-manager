package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.Address;
import com.manager.shared.domain.model.Document;
import com.manager.shared.domain.model.Email;
import com.manager.shared.domain.model.Phone;
import com.manager.shared.domain.model.validators.DocumentValidatorFactory;
import com.manager.shared.domain.validation.DomainValidationException;
import com.manager.shared.domain.validation.ValidationNotification;

import java.time.LocalDate;

public class RegisterPatient {

    private final IPatientRepository repository;

    public RegisterPatient(IPatientRepository repository) {
        this.repository = repository;
    }

    public void execute(RegisterPatientRequest request) {
        ValidationNotification notification = new ValidationNotification();

        try {
            new Document(request.documentValue(), request.documentCountry(),
                    DocumentValidatorFactory.getValidator(request.documentCountry()));
        } catch (IllegalArgumentException e) {
            notification.addError("- Documento: " + e.getMessage());
        }

        // Validação do Email
        try {
            new Email(request.email());
        } catch (IllegalArgumentException e) {
            notification.addError("- E-mail: " + e.getMessage());
        }

        // Validação do Telefone
        try {
            new Phone(request.phoneCountryCode(), request.phoneNumber());
        } catch (IllegalArgumentException e) {
            notification.addError("- Telefone: " + e.getMessage());
        }

        // Validação do Endereço
        try {
            new Address(request.street(), request.number(), request.complement(),
                    request.neighborhood(), request.city(), request.state(),
                    request.zipCode(), request.countryCode());
        } catch (IllegalArgumentException e) {
            notification.addError("- Endereço: " + e.getMessage());
        }

        // Se houver qualquer erro acumulado, lançamos uma exceção customizada com todos eles
        if (notification.hasErrors()) {
            throw new DomainValidationException(notification.getErrorMessage());
        }

        // 1. Criar os Value Objects (Validação de formato automática)
        Document doc = new Document(
                request.documentValue(),
                request.documentCountry(),
                DocumentValidatorFactory.getValidator(request.documentCountry())
        );

        Email email = new Email(request.email());

        Phone phone = new Phone(request.phoneCountryCode(), request.phoneNumber());

        Address address = new Address(
                request.street(), request.number(), request.complement(),
                request.neighborhood(), request.city(), request.state(),
                request.zipCode(), request.countryCode()
        );

        // 2. Instanciar a Entidade (Validação de Regras de Negócio)
        Patient patient = new Patient(
                request.name(),
                LocalDate.parse(request.birthDate()),
                doc,
                email,
                phone,
                address
        );

        // 3. Persistir
        repository.save(patient);
        System.out.println("Paciente " + patient.getName() + " registrado com sucesso!");
    }
}