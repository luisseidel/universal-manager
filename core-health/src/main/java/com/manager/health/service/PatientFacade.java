package com.manager.health.service;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.PatientSearchCriteria;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.model.UpdatePatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.mapper.PatientMapper;
import com.manager.health.usecase.*;
import com.manager.shared.domain.model.dto.PagedResponse;

public class PatientFacade implements IPatientFacade {

    private final ListPatients listPatients;
    private final SearchPatient searchPatient;
    private final RegisterPatient registerPatient;
    private final UpdatePatient updatePatient;
    private final DeletePatient deletePatient;
    private final PatientMapper patientMapper;

    public PatientFacade(IPatientRepository repository) {
        this.patientMapper = new PatientMapper();
        this.listPatients = new ListPatients(repository, patientMapper);
        this.searchPatient = new SearchPatient(repository);
        this.registerPatient = new RegisterPatient(repository);
        this.updatePatient = new UpdatePatient(repository, patientMapper);
        this.deletePatient = new DeletePatient(repository);
    }

    @Override
    public PatientResponse findByDocument(String document) {
        return searchPatient.execute(document);
    }

    @Override
    public PagedResponse<PatientResponse> listPaged(PatientSearchCriteria criteria) {
        return listPatients.execute(criteria);
    }

    @Override
    public void create(RegisterPatientRequest request) {
        registerPatient.execute(request);
    }

    @Override
    public void update(String document, UpdatePatientRequest request) {
        updatePatient.execute(document, request);
    }

    @Override
    public void delete(String document) {
        deletePatient.execute(document);
    }


}
