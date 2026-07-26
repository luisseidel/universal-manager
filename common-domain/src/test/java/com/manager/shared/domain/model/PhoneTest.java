package com.manager.shared.domain.model;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.entity.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneTest {

    @Test
    @DisplayName("Deve validar e formatar telefone dos EUA corretamente")
    void shouldValidateUSAPhone() {
        // ARRANGE
        String ddi = "1";
        String input = "2025550123"; // Washington DC area code

        // ACT
        Phone phone = new Phone(input, Country.fromCodeOrDdi(ddi));

        // ASSERT
        assertEquals("12025550123", phone.getInternationalRaw());
        assertEquals("(202) 555-0123", phone.getFormatted());
        assertEquals("+1 (202) 555-0123", phone.getInternationalFormatted());
    }

    @Test
    @DisplayName("Deve rejeitar telefone USA iniciando com 0 ou 1 no código de área")
    void shouldRejectInvalidUSAPhone() {
        // ARRANGE
        String ddi = "1";
        String invalidInput = "1025550123"; // Começa com 1

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidInput, Country.fromCodeOrDdi(ddi)));
    }

}
