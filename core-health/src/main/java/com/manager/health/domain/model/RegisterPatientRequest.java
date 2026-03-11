package com.manager.health.domain.model;

public record RegisterPatientRequest(
        String name,
        String birthDate,
        String documentValue,
        String documentCountry,
        String email,
        String phoneCountryCode,
        String phoneNumber,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String countryCode
) {}
