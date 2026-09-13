package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.entity.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class USADocumentValidatorTest {

    private final USADocumentValidator validator = new USADocumentValidator();

    @Test
    void clean_shouldReturnBlankString_whenNullArg() {
        String docNumber = null;

        String result = validator.clean(docNumber);

        Assertions.assertEquals("", result);
    }

    @Test
    void clean_shouldReturnNumeric_whenIvalidChars() {
        String docNumber = "12345678900=asd";

        String cleaned = validator.clean(docNumber);

        Assertions.assertEquals("12345678900", cleaned);
    }

    @Test
    void isValid_shouldReturnFalse_whenInvalidArea() {
        String invalidSSNArea = "902006789";
        String invalidSSNArea2 = "000006789";
        String invalidSSNArea3 = "666006789";

        boolean cleaned = validator.isValid(invalidSSNArea);
        boolean cleaned2 = validator.isValid(invalidSSNArea2);
        boolean cleaned3 = validator.isValid(invalidSSNArea3);

        Assertions.assertFalse(cleaned);
        Assertions.assertFalse(cleaned2);
        Assertions.assertFalse(cleaned3);
    }

    @Test
    @DisplayName("Deve validar e formatar um SSN americano corretamente")
    void constructor_shouldValidateAndFormatSSN_whenValidNumber() {
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
    void constructor_shouldInvalidateSSN_whenZeroGroup() {
        // ARRANGE
        String invalidSsn = "123006789";
        Executable exec = () -> new Document(invalidSsn, Country.UNITED_STATES);

        // ACT
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, exec);

        //ASSERT
        Assertions.assertNotNull(ex);
    }

    @Test
    void format_shouldReturn_whenInvalidLength() {
        String docNumber = "12344455667788";

        String result = validator.format(docNumber);

        Assertions.assertEquals("12344455667788", result);
    }
}
