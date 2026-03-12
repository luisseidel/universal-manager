package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.repository.IPatientIRepository;
import com.manager.health.domain.specs.ActivePatientSpecification;
import com.manager.health.domain.specs.PatientNameLikeSpecification;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;

import java.util.List;

public class ListPatients {

    private final IPatientIRepository repository;

    public ListPatients(IPatientIRepository repository) {
        this.repository = repository;
    }

    public PagedResponse<PatientResponse> execute(String query, int page, int size) {
        ISpecification<Patient> spec = new PatientNameLikeSpecification(query);
        spec = spec.and(new ActivePatientSpecification(true));

        List<Patient> paged = repository.findPaged(spec, page, size);
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