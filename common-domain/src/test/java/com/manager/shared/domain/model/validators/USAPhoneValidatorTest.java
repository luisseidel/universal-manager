package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class USAPhoneValidatorTest {

    @Test
    @DisplayName("Clean should return empty string when a null phone")
    void clean_shouldReturnEmptyString_withNullPhone() {
        //Arrange
        String ddi = "1";
        String nullPhoneNumber = null;
        var country = Country.fromCodeOrDdi(ddi);
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String result = validator.clean(nullPhoneNumber);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Clean should return a cleaned phone when a valid argument is passed")
    void clean_shouldReturnCleanPhone_withValidArgs() {
        //Arrange
        String ddi = "1";
        String number = "5137487288aaa";
        var country = Country.fromCodeOrDdi(ddi);
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String result = validator.clean(number);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("5137487288", result);
    }

    @Test
    @DisplayName("IsValid should return true when a valid number is passed")
    void isValid_shouldReturnTrue_whenValidNumber() {
        //Arrange
        String number = "5552234567";
        var country = Country.fromCodeOrDdi("1");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        boolean result = validator.isValid(number);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false when invalid number is passed")
    void isValid_shouldReturnFalse_whenInvalidNumber() {
        //Arrange
        String number = "5199999888888";
        var country = Country.fromCodeOrDdi("1");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        boolean result = validator.isValid(number);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Format should return a formatted phone, when valid number is passed")
    void format_shouldReturnFormatted_whenValidNumber() {
        //Arrange
        String number = "5552234567";
        var country = Country.fromCodeOrDdi("1");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String formatted = validator.format(number);

        //Assert
        Assertions.assertEquals("(555) 223-4567", formatted);
    }

    @Test
    @DisplayName("Format should return a cleaned number when wrong number type")
    void format_shouldReturnCleanedNumber_whenWrongNumberType() {
        //Arrange
        String number = "51a374877222222s";
        var country = Country.fromCodeOrDdi("1");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String formatted = validator.format(number);

        //Assert
        Assertions.assertEquals("51374877222222", formatted);
    }

}
