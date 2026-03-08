package com.manager.health.domain.repository;

import com.manager.health.domain.model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPatientRepository {
    void save(Patient patient);
    List<Patient> findAll();
    Optional<Patient> findById(UUID id);
    Optional<Patient> findByDocument(String document);
    void delete(UUID id);
}