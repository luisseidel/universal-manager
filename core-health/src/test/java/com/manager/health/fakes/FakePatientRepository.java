package com.manager.health.fakes;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;

import java.util.*;

public class FakePatientRepository implements IPatientRepository {

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
    public PagedResponse<Patient> findPaged(ISpecification<Patient> spec, int page, int pageSize) {
        int skip = (page - 1) * pageSize;
        long total = database.values().stream().filter(spec::isSatisfiedBy).count();

        List<Patient> result = database.values().stream()
                .filter(spec::isSatisfiedBy)
                .skip(skip)
                .limit(pageSize)
                .toList();

        return PagedResponse.of(result, page, pageSize, total);
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
