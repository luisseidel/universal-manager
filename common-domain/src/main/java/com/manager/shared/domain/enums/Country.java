package com.manager.shared.domain.enums;

import java.util.Arrays;

public enum Country {

    UNITED_STATES("1", "US", "United States"),
    BRAZIL("55", "BR", "Brasil"),
    ;

    private final String phoneDdi;   // ITU-T E.164
    private final String codeAlpha2; // ISO 3166-1 Alpha-2
    private final String fullName;

    Country(String phoneDdi, String codeAlpha2, String fullName) {
        this.phoneDdi = phoneDdi;
        this.codeAlpha2 = codeAlpha2;
        this.fullName = fullName;
    }

    public static Country fromCodeOrDdi(String value) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("O código do país não pode ser nulo ou vazio");
        }

        return Arrays.stream(Country.values())
                .filter(c -> c.codeAlpha2.equalsIgnoreCase(value)
                    || c.phoneDdi.equals(value)
                    || c.name().equalsIgnoreCase(value)
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("País não suportado para o código: " + value));

    }

    public String getCodeAlpha2() {
        return codeAlpha2;
    }

    public String getPhoneDdi() {
        return phoneDdi;
    }

    public String getFullName() {
        return fullName;
    }

}
