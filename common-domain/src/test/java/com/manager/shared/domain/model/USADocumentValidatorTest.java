package com.manager.shared.domain.model;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.entity.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class USADocumentValidatorTest {

    @Test
    @DisplayName("Deve validar e formatar um SSN americano corretamente")
    void shouldValidateAndFormatSSN() {
        // ARRANGE
        String input = "123456789";

        // ACT
        Document doc = new Document(input, Country.UNITED_STATES);

        // ASSERT
        assertEquals("123-45-6789", doc.getFormatted());
        assertEquals("123456789", doc.getRawValue());
    }

    @Test
    @DisplayName("Deve invalidar SSN com grupo 00")
    void shouldInvalidateSSNWithZeroGroup() {
        // ARRANGE
        String invalidSsn = "123006789";

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            new Document(invalidSsn, Country.UNITED_STATES);
        });
    }
}
