package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;

import java.util.Objects;

public class Address {

    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;
    private final Country country; // ISO 3166-1 (BR, US, etc)

    public Address(String street, String number, String complement, String neighborhood,
                   String city, String state, String zipCode, Country country) {

        validate(street, city, state, zipCode, country);

        this.street = street.toUpperCase();
        this.number = cleanNumbers(number);
        this.complement = complement.toUpperCase();
        this.neighborhood = neighborhood.toUpperCase();
        this.city = city.toUpperCase();
        this.state = state.toUpperCase();
        this.zipCode = cleanNumbers(zipCode);
        this.country = country;
    }

    private void validate(String street, String city, String state, String zipCode, Country country) {
        Objects.requireNonNull(country, "O país do documento é obrigatório");

        if (isNullOrBlank(street) || isNullOrBlank(city) || isNullOrBlank(state) || isNullOrBlank(zipCode)) {
            throw new IllegalArgumentException("Campos obrigatórios do endereço não podem estar vazios.");
        }

        // Validação específica de CEP por país
        if ("BR".equals(country.getCodeAlpha2()) && !zipCode.replaceAll("[^0-9]", "").matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP brasileiro deve conter 8 dígitos.");
        }

        if ("US".equals(country.getCodeAlpha2()) && !zipCode.replaceAll("[^0-9]", "").matches("\\d{5}(-\\d{4})?")) {
            throw new IllegalArgumentException("ZIP Code americano inválido.");
        }
    }

    private String cleanNumbers(String number) {
        return number.replaceAll("[^0-9]", "");
    }

    private boolean isNullOrBlank(String s) {
        return s == null || s.trim().isBlank();
    }

    public String getFullAddress() {
        return String.format("%s, %s - %s, %s/%s - %s", street, number, neighborhood, city, state, zipCode);
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Country getCountry() {
        return country;
    }
}
