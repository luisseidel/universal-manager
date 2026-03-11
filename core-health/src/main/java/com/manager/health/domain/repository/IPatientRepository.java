package com.manager.health.domain.repository;

import com.manager.health.domain.model.Patient;
import com.manager.shared.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IPatientRepository extends Repository<Patient, UUID> {
    Optional<Patient> findByDocument(String document);
}