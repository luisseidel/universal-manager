package com.manager.health.domain.model;

public record PatientSearchCriteria(
    String name,
    String documentNumber,
    Boolean active,
    int page,
    int size
) {
}
