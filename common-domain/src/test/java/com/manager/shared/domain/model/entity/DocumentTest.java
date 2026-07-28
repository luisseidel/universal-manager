package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void constructor_shouldCreate_withValidParameters() {
        String brDocNumber = "50663102154";
        Country country = Country.BRAZIL;

        Document document = new Document(brDocNumber, country);

        Assertions.assertNotNull(document);
        Assertions.assertEquals(brDocNumber, document.getRawValue());
        Assertions.assertEquals("506.631.021-54", document.getFormatted());
    }

    @Test
    void constructor_shouldThrowNullpointer_withNullCountry() {
        String brDocNumber = "50663102154";
        Country country = null;

        NullPointerException exception = Assertions.assertThrows(
            NullPointerException.class, () -> new Document(brDocNumber, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("O país do documento é obrigatório", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullCountry() {
        String brDocNumber = null;
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Document(brDocNumber, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Documento inválido", exception.getMessage());
    }

}
