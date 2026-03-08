package com.manager.health.service;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.usecase.DeletePatient;
import com.manager.health.usecase.ListPatients;
import com.manager.health.usecase.RegisterPatient;
import com.manager.health.usecase.SearchPatient;

import java.util.List;

public class PatientFacade implements IPatientFacade {

    private final RegisterPatient registerPatient;
    private final ListPatients listPatients;
    private final SearchPatient searchPatient;
    private final DeletePatient deletePatient;

    public PatientFacade(IPatientRepository repository) {
        this.registerPatient = new RegisterPatient(repository);
        this.listPatients = new ListPatients(repository);
        this.searchPatient = new SearchPatient(repository);
        this.deletePatient = new DeletePatient(repository);
    }

    @Override
    public void registerPatient(RegisterPatientRequest request) {
        registerPatient.execute(request);
    }

    @Override
    public List<PatientResponse> listPatients() {
        return listPatients.execute();
    }

    @Override
    public PatientResponse findByDocument(String document) {
        return searchPatient.execute(document);
    }

    @Override
    public void delete(String document) {
        this.deletePatient.execute(document);
    }
}
