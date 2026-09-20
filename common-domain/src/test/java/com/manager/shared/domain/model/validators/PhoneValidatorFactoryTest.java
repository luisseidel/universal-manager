package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PhoneValidatorFactoryTest {

    @Test
    @DisplayName("Get Validator should return exception when null country")
    void getValidator_shouldReturnException_whenNullCountry() {
        Country country = null;
        Executable exec = () -> PhoneValidatorFactory.getValidator(country);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, exec);

        Assertions.assertNotNull(ex);
    }

    @Test
    @DisplayName("Get Validator should return exception when not implemented country")
    void getValidator_shouldReturnException_whenNotImplementedCountry() {
        Country country = Country.UNITED_KINGDOM;
        Executable exec = () -> PhoneValidatorFactory.getValidator(country);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, exec);

        Assertions.assertNotNull(ex);
    }

    @Test
    @DisplayName("Get Validator, Should return Validator when valid country")
    void getValidator_shouldReturnValidator_whenValidCountry() {
        Country country = Country.BRAZIL;

        IPhoneValidator validator = PhoneValidatorFactory.getValidator(country);

        Assertions.assertNotNull(validator);
    }

}
