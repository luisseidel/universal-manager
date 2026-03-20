package com.manager.health.service;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.PatientSearchCriteria;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.shared.service.IFacade;

public interface IPatientFacade extends IFacade<RegisterPatientRequest, PatientResponse, String, PatientSearchCriteria> {
    PatientResponse findByDocument(String document);
}
