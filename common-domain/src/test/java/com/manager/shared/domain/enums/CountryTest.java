package com.manager.shared.domain.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void shouldCreate_withRecognizedCode() {
        String isoCode = "BR";
        Country country = Country.fromCodeOrDdi(isoCode);

        Assertions.assertNotNull(country);
        Assertions.assertEquals(isoCode, country.getCodeAlpha2());
        Assertions.assertEquals("Brasil", country.getFullName());
        Assertions.assertEquals("55", country.getPhoneDdi());
    }

    @Test
    void shouldCreate_withRecognizedDDI() {
        String ddi = "55";
        Country country = Country.fromCodeOrDdi(ddi);

        Assertions.assertNotNull(country);
        Assertions.assertEquals("BR", country.getCodeAlpha2());
        Assertions.assertEquals("Brasil", country.getFullName());
        Assertions.assertEquals("55", country.getPhoneDdi());
    }

    @Test
    void shouldThrowIllegalArgumentException_withNullParameter() {
        String isoCode = null;

        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, () -> Country.fromCodeOrDdi(isoCode)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("O código do país não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentException_withBlankParameter() {
        String isoCode = "   ";

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> Country.fromCodeOrDdi(isoCode)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("O código do país não pode ser nulo ou vazio", exception.getMessage());
    }

}
