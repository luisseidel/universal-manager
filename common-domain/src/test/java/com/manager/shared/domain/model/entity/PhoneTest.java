package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void constructor_shouldCreate_withValidParameters() {
        String validNumber = "51999999999";
        Country country = Country.BRAZIL;

        Phone phone = new Phone(validNumber, country);

        Assertions.assertNotNull(phone);
        Assertions.assertEquals(validNumber, phone.getRawNumber());
        Assertions.assertEquals("(51) 99999-9999", phone.getFormatted());
        Assertions.assertEquals("5551999999999", phone.getInternationalRaw());
        Assertions.assertEquals(country, phone.getCountry());
        Assertions.assertEquals("+55 (51) 99999-9999", phone.getInternationalFormatted());
    }

    @Test
    void constructor_shouldThrowNullPointer_withNullCountry() {
        String validNumber = "51999999999";
        Country country = null;

        NullPointerException exception = Assertions.assertThrows(
            NullPointerException.class, () -> new Phone(validNumber, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("O país do documento é obrigatório", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullPhone() {
        String validNumber = null;
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Phone(validNumber, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Número de telefone inválido para o DDI +" + country.getPhoneDdi(), exception.getMessage());
    }

}
