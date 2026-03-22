package com.manager.health.mapper;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.model.UpdatePatientRequest;
import com.manager.shared.domain.model.IMapper;
import com.manager.shared.domain.model.entity.Address;
import com.manager.shared.domain.model.entity.Document;
import com.manager.shared.domain.model.entity.Email;
import com.manager.shared.domain.model.entity.Phone;
import com.manager.shared.domain.model.validators.DocumentValidatorFactory;

import java.time.LocalDate;

public class PatientMapper implements IMapper<Patient, PatientResponse, RegisterPatientRequest, UpdatePatientRequest> {

    @Override
    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getName(),
                patient.getDocument().getFormatted(),
                patient.getPhone().getFormatted(),
                patient.getEmail().getValue(),
                patient.getAddress().getFullAddress()
        );
    }

    @Override
    public Patient toDomain(RegisterPatientRequest request) {
        return new Patient(
            request.name(),
            LocalDate.parse(request.birthDate()),
            new Document(request.documentValue(), request.documentCountry(),
                    DocumentValidatorFactory.getValidator(request.documentCountry())),
            new Email(request.email()),
            new Phone(request.phoneCountryCode(), request.phoneNumber()),
            new Address(request.street(), request.number(), request.complement(),
                    request.neighborhood(), request.city(), request.state(),
                    request.zipCode(), request.countryCode()),
            true
        );
    }

    @Override
    public void updateEntity(Patient entity, UpdatePatientRequest request) {
        Email email = new Email(request.email());
        Phone phone = new Phone(request.phoneCountryCode(), request.phoneNumber());
        Address address = new Address(
                request.street(), request.number(), request.complement(), request.neighborhood(),
                request.city(), request.state(), request.zipCode(), request.countryCode()
        );

        entity.setName(request.name());
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setAddress(address);
        entity.setActive(request.active());
    }
}
