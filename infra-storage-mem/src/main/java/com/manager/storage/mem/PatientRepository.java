package com.manager.storage.mem;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientRepository;

import java.util.*;

public class PatientRepository implements IPatientRepository {

    private final Map<UUID, Patient> database = new HashMap<>();

    @Override
    public void save(Patient patient) {
        database.put(patient.getId(), patient);
        System.out.println("[DB Log] Paciente salvo com sucesso: " + patient.getName());
    }

    @Override
    public Optional<Patient> findByDocument(String documentNumber) {
        return database.values().stream()
                .filter(p -> p.getDocument().getRawValue().equals(documentNumber))
                .findFirst();
    }

    @Override
    public Optional<Patient> findById(UUID id) {
        return database.values().stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Patient> findAll() {
        return database.values().stream().toList();
    }
}