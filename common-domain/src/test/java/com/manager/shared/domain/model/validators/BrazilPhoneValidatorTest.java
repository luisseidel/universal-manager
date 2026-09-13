package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class BrazilPhoneValidatorTest {

    @Test
    @DisplayName("Should return empty string when a null phone")
    void clean_shouldReturnEmptyString_withNullPhone() {
        //Arrange
        String ddi = "55";
        String nullPhoneNumber = null;
        var validator = PhoneValidatorFactory.getValidator(Country.fromCodeOrDdi(ddi));

        //Act
        String result = validator.clean(nullPhoneNumber);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("", result);
    }

    @Test
    void clean_shouldReturnCleanPhone_withValidArgs() {
        //Arrange
        String ddi = "55";
        String number = "5137487288aaa";
        var validator = PhoneValidatorFactory.getValidator(Country.fromCodeOrDdi(ddi));

        //Act
        String result = validator.clean(number);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("5137487288", result);
    }

    @Test
    void isLandlineNumber_shouldThrowException_whenNullArg() throws Exception {
        //Arrange
        String number = null;
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isLandlineNumber", String.class);
        method.setAccessible(true);
        Executable action = () -> method.invoke(validator, number);

        //Act
        InvocationTargetException ex = Assertions.assertThrows(InvocationTargetException.class, action);

        //Assert
        Assertions.assertNotNull(ex);
    }

    @Test
    void isLandlineNumber_shouldReturnTrue_whenArgsHasLengthTen() throws Exception {
        //Arrange
        String number = "5137487288";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isLandlineNumber", String.class);
        method.setAccessible(true);

        //Act
        boolean result = (boolean) method.invoke(validator, number);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    void isLandlineNumber_shouldReturnFalse_whenArgsHasLengthDifferentTen() throws Exception {
        //Arrange
        String number = "513748728";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isLandlineNumber", String.class);
        method.setAccessible(true);

        //Act
        boolean result = (boolean) method.invoke(validator, number);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    void isMobilePhone_shouldThrowException_whenNullArg() throws Exception {
        //Arrange
        String number = null;
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isMobilePhone", String.class);
        method.setAccessible(true);
        Executable action = () -> method.invoke(validator, number);

        //Act
        InvocationTargetException ex = Assertions.assertThrows(InvocationTargetException.class, action);

        //Assert
        Assertions.assertNotNull(ex);
    }

    @Test
    void isMobilePhone_shouldReturnFalse_whenArgsHasLengthDifferentEleven() throws Exception {
        //Arrange
        String number = "519999999884";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isMobilePhone", String.class);
        method.setAccessible(true);

        //Act
        boolean result = (boolean) method.invoke(validator, number);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    void isMobilePhone_shouldReturnTrue_whenArgsHasLengthEleven() throws Exception {
        //Arrange
        String number = "51999999999";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);
        Method method = BrazilPhoneValidator.class.getDeclaredMethod("isMobilePhone", String.class);
        method.setAccessible(true);

        //Act
        boolean result = (boolean) method.invoke(validator, number);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    void isValid_shouldReturnTrue_whenPhoneNumberValid() {
        //Arrange
        String number = "51999998888";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        boolean result = validator.isValid(number);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    void isValid_shouldReturnFalse_whenPhoneNumberInvalid() {
        //Arrange
        String number = "5199999888888";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        boolean result = validator.isValid(number);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    void format_shouldReturnFormatted_whenValidMobileNumber() {
        //Arrange
        String number = "51999998888";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String formatted = validator.format(number);

        //Assert
        Assertions.assertEquals("(51) 99999-8888", formatted);
    }

    @Test
    void format_shouldReturnFormatted_whenValidLandlineNumber() {
        //Arrange
        String number = "5137487722";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String formatted = validator.format(number);

        //Assert
        Assertions.assertEquals("(51) 3748-7722", formatted);
    }

    @Test
    void format_shouldReturnCleanedNumber_whenWrongNumberType() {
        //Arrange
        String number = "51a374877222222s";
        var country = Country.fromCodeOrDdi("55");
        var validator = PhoneValidatorFactory.getValidator(country);

        //Act
        String formatted = validator.format(number);

        //Assert
        Assertions.assertEquals("51374877222222", formatted);
    }

}
