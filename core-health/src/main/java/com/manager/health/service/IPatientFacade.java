package com.manager.health.service;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;

import java.util.List;

public interface IPatientFacade {

    void registerPatient(RegisterPatientRequest request);
    List<PatientResponse> listPatients();
    PatientResponse findByDocument(String document);
    void delete(String document);
}
