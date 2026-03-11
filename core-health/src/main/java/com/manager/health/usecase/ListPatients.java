package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.shared.domain.model.dto.PagedResponse;

import java.util.List;

public class ListPatients {

    private final IPatientRepository repository;

    public ListPatients(IPatientRepository repository) {
        this.repository = repository;
    }

    public PagedResponse<PatientResponse> execute(int page, int size) {
        List<Patient> paged = repository.findPaged(page, size);
        int total = repository.count();

        List<PatientResponse> items = paged.stream().map(
            p -> new PatientResponse(
                    p.getName(),
                    p.getDocument().getFormatted(),
                    p.getPhone().getFormatted(),
                    p.getEmail().getValue(),
                    p.getAddress().getFullAddress()
        )).toList();

        return PagedResponse.of(items, page, size, total);
    }
}