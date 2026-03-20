package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.PatientSearchCriteria;
import com.manager.health.domain.repository.IPatientIRepository;
import com.manager.health.domain.specs.ActivePatientSpecification;
import com.manager.health.domain.specs.PatientDocumentSpecification;
import com.manager.health.domain.specs.PatientNameLikeSpecification;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;

import java.util.List;

public class ListPatients {

    private final IPatientIRepository repository;

    public ListPatients(IPatientIRepository repository) {
        this.repository = repository;
    }

    public PagedResponse<PatientResponse> execute(PatientSearchCriteria criteria) {
        ISpecification<Patient> spec = (patient) -> true;

        if (criteria.name() != null && !criteria.name().isBlank()) {
            spec = spec.and(new PatientNameLikeSpecification(criteria.name()));
        }

        if (criteria.active() != null) {
            spec = spec.and(new ActivePatientSpecification(criteria.active()));
        }

        if (criteria.documentNumber() != null && !criteria.documentNumber().isBlank()) {
            spec = spec.and(new PatientDocumentSpecification(criteria.documentNumber()));
        }

        List<Patient> paged = repository.findPaged(spec, criteria.page(), criteria.size());
        long total = repository.count(spec);

        List<PatientResponse> items = paged.stream().map(
            p -> new PatientResponse(
                    p.getName(),
                    p.getDocument().getFormatted(),
                    p.getPhone().getFormatted(),
                    p.getEmail().getValue(),
                    p.getAddress().getFullAddress()
        )).toList();

        return PagedResponse.of(items, criteria.page(), criteria.size(), total);
    }
}