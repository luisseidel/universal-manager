package com.manager.shared.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    @DisplayName("Constructor should create with valid parameter")
    void constructor_shouldCreate_withValidParameter() {
        String e = "asd@asd.com";

        Email email = new Email(e);

        Assertions.assertNotNull(email);
        Assertions.assertEquals("asd@asd.com", email.getValue());
    }

    @Test
    @DisplayName("Constructor should not create with null value")
    void constructor_shouldNotCreate_withNullValue() {
        String nullEmail = null;

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(nullEmail));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should not create entity with invalid parameter")
    void constructor_shouldNotCreate_withInvalidParameter() {
        String invalid = "@asdc.co@dasl.c.bn";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(invalid));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("E-mail inválido", exception.getMessage());
    }

}
