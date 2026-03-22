package com.manager.storage.mem;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;

import java.util.*;

public class PatientIPatientRepository implements IPatientRepository {

    private final Map<UUID, Patient> database = new HashMap<>();

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
    public PagedResponse<Patient> findPaged(ISpecification<Patient> spec, int page, int size) {
        int skip = (page - 1) * size;
        long total = database.values().stream().filter(spec::isSatisfiedBy).count();

        List<Patient> result = database.values().stream()
                .filter(spec::isSatisfiedBy)
                .skip(skip)
                .limit(size)
                .toList();

        return PagedResponse.of(result, page, size, total);
    }

    @Override
    public void save(Patient patient) {
        database.put(patient.getId(), patient);
        System.out.println("[DB Log] Paciente salvo com sucesso: " + patient.getName());
    }

    @Override
    public void update(Patient patient) {
        save(patient);
    }

    @Override
    public void delete(UUID id) {
        database.remove(id);
    }

    @Override
    public long count(ISpecification<Patient> spec) {
        return database.values().stream()
                .filter(spec::isSatisfiedBy)
                .count();
    }
}