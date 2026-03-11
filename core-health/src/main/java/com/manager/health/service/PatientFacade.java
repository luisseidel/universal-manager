package com.manager.health.service;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.usecase.*;
import com.manager.shared.domain.model.dto.PagedResponse;

public class PatientFacade implements IPatientFacade {

    private final ListPatients listPatients;
    private final SearchPatient searchPatient;
    private final RegisterPatient registerPatient;
    private final UpdatePatient updatePatient;
    private final DeletePatient deletePatient;

    public PatientFacade(IPatientRepository repository) {
        this.listPatients = new ListPatients(repository);
        this.searchPatient = new SearchPatient(repository);
        this.registerPatient = new RegisterPatient(repository);
        this.updatePatient = new UpdatePatient(repository);
        this.deletePatient = new DeletePatient(repository);
    }

    @Override
    public PatientResponse findByDocument(String document) {
        return searchPatient.execute(document);
    }

    @Override
    public PagedResponse<PatientResponse> listPaged(int page, int size) {
        return listPatients.execute(page, size);
    }

    @Override
    public void create(RegisterPatientRequest request) {
        registerPatient.execute(request);
    }

    @Override
    public void update(String document, RegisterPatientRequest request) {
        updatePatient.execute(document, request);
    }

    @Override
    public void delete(String document) {
        deletePatient.execute(document);
    }


}
