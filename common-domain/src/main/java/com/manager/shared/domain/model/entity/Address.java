package com.manager.shared.domain.model.entity;

public class Address {

    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String countryCode; // ISO 3166-1 (BR, US, etc)

    public Address(String street, String number, String complement, String neighborhood,
                   String city, String state, String zipCode, String countryCode) {

        // Validação de consistência básica
        validate(street, city, state, zipCode, countryCode);

        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = cleanZipCode(zipCode);
        this.countryCode = countryCode.toUpperCase();
    }

    private void validate(String street, String city, String state, String zipCode, String countryCode) {
        if (isNullOrBlank(street) || isNullOrBlank(city) || isNullOrBlank(state) || isNullOrBlank(zipCode)) {
            throw new IllegalArgumentException("Campos obrigatórios do endereço não podem estar vazios.");
        }

        // Validação específica de CEP por país
        if ("BR".equals(countryCode) && !zipCode.replaceAll("[^0-9]", "").matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP brasileiro deve conter 8 dígitos.");
        }

        if ("US".equals(countryCode) && !zipCode.replaceAll("[^0-9]", "").matches("\\d{5}(-\\d{4})?")) {
            throw new IllegalArgumentException("ZIP Code americano inválido.");
        }
    }

    private String cleanZipCode(String zip) {
        return zip.replaceAll("[^0-9]", "");
    }

    private boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public String getFullAddress() {
        return String.format("%s, %s - %s, %s/%s - %s", street, number, neighborhood, city, state, zipCode);
    }

}
