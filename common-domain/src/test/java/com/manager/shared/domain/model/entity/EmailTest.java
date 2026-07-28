package com.manager.shared.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void constructor_shouldCreate_withValidParameter() {
        String e = "asd@asd.com";

        Email email = new Email(e);

        Assertions.assertNotNull(email);
        Assertions.assertEquals("asd@asd.com", email.getValue());
    }

    @Test
    void constructor_shouldNotCreate_withNullValue() {
        String nullEmail = null;

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(nullEmail));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void constructor_shouldNotCreate_withInvalidParameter() {
        String invalid = "@asdc.co@dasl.c.bn";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(invalid));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("E-mail inválido", exception.getMessage());
    }

}
