package com.manager.health.usecase;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.PatientSearchCriteria;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.domain.specs.ActivePatientSpecification;
import com.manager.health.domain.specs.PatientDocumentSpecification;
import com.manager.health.domain.specs.PatientNameLikeSpecification;
import com.manager.health.mapper.PatientMapper;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;

public class ListPatients {

    private final IPatientRepository repository;
    private final PatientMapper mapper;

    public ListPatients(IPatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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

        PagedResponse<Patient> paged = repository.findPaged(spec, criteria.page(), criteria.size());

        return paged.map(mapper::toResponse);
    }
}