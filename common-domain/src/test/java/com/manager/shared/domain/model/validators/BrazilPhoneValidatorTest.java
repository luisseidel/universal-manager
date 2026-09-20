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
    @DisplayName("Clean should return a cleaned phone when valid args")
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
    @DisplayName("IsLandlineNumber should throw exception when null arg")
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
    @DisplayName("isLandlineNumber should return true, when args has length 10")
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
    @DisplayName("IsLandlineNumber should return false when args has length different of ten")
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
    @DisplayName("IsMobilePhone should throw exception when null arg")
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
    @DisplayName("IsMobilePhone should return false when args has length different of eleven")
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
    @DisplayName("isMobilePhone should return true, when args has length eleven")
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
    @DisplayName("IsValid should return true, when valid phone number")
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
    @DisplayName("IsValid should return false when invalid phone number")
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
    @DisplayName("Format should return formatted when valid mobile number")
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
    @DisplayName("Format should return formatted when valid landline number")
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
    @DisplayName("Format should return cleaned number when wrong number type")
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
