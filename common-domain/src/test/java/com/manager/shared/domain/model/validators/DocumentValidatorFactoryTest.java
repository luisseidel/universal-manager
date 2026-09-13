package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DocumentValidatorFactoryTest {

    @Test
    void getValidator_shouldReturnException_whenNullCountry() {
        Country country = null;
        Executable exec = () -> DocumentValidatorFactory.getValidator(null);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, exec);

        Assertions.assertNotNull(ex);
    }

    @Test
    void getValidator_shouldReturnException_whenNotImplementedCountry() {
        Country country = Country.UNITED_KINGDOM;
        Executable exec = () -> DocumentValidatorFactory.getValidator(country);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, exec);

        Assertions.assertNotNull(ex);
    }

    @Test
    void getValidator_shouldReturnValidator_whenValidCountry() {
        Country country = Country.BRAZIL;

        IDocumentValidator validator = DocumentValidatorFactory.getValidator(country);

        Assertions.assertNotNull(validator);
    }

}
