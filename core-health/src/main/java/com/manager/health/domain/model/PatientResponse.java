package com.manager.health.domain.model;

public record PatientResponse(
        String name,
        String document,
        String phone,
        String email,
        String address
) {}