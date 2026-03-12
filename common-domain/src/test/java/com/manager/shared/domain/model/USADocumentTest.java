package com.manager.shared.domain.model;

import com.manager.shared.domain.model.entity.Document;
import com.manager.shared.domain.model.validators.IDocumentValidator;
import com.manager.shared.domain.model.validators.USADocumentValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class USADocumentTest {

    @Test
    @DisplayName("Deve validar e formatar um SSN americano corretamente")
    void shouldValidateAndFormatSSN() {
        // ARRANGE
        String input = "123456789";
        IDocumentValidator validator = new USADocumentValidator();

        // ACT
        Document doc = new Document(input, "US", validator);

        // ASSERT
        assertEquals("123-45-6789", doc.getFormatted());
        assertEquals("123456789", doc.getRawValue());
    }

    @Test
    @DisplayName("Deve invalidar SSN com grupo 00")
    void shouldInvalidateSSNWithZeroGroup() {
        // ARRANGE
        String invalidSsn = "123006789";
        IDocumentValidator validator = new USADocumentValidator();

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            new Document(invalidSsn, "US", validator);
        });
    }
}
