package com.manager.health.domain.repository;

import com.manager.health.domain.model.Patient;
import com.manager.shared.repository.IRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPatientIRepository extends IRepository<Patient, UUID> {
    Optional<Patient> findByDocument(String document);
}