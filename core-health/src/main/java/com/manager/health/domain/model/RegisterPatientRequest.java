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
        String houseNumber,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String countryCode
) {}
