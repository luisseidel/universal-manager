package com.manager.health.domain.model;

public record UpdatePatientRequest(
        String name,
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
        String countryCode,
        boolean active
) {
}
