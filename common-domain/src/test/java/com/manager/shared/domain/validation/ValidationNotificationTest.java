package com.manager.shared.domain.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

class ValidationNotificationTest {

    @Test
    @DisplayName("Constructor should initialize without errors")
    void constructor_shouldStartEmpty_whenNoErros() {
        ValidationNotification notification = new ValidationNotification();

        Assertions.assertFalse(notification.hasErrors());
        Assertions.assertTrue(notification.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Should accumulate multiple validation errors")
    void addError_shouldAccumulateErrors_whenErros() {
        ValidationNotification notification = new ValidationNotification();

        notification.addError("Name is Required");
        notification.addError("CPF is invalid");

        Assertions.assertTrue(notification.hasErrors());
        Assertions.assertEquals(2, notification.getErrors().size());
        Assertions.assertTrue(notification.getErrors().contains("Name is Required"));
        Assertions.assertTrue(notification.getErrors().contains("CPF is invalid"));
    }

    @Test
    @DisplayName("AddError should not add, when message is null or blank")
    void addError_shouldNotAddErrors_whenMessageIsNullOrBlank() {
        ValidationNotification notification = new ValidationNotification();
        String error1 = null;
        String error2 = "   ";

        notification.addError(error1);
        notification.addError(error2);

        Assertions.assertNotNull(notification.getErrors());
        Assertions.assertTrue(notification.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Should add error conditionally when the condition is true")
    void addErrorIf_shouldAddError_whenConditionIsTrue() {
        ValidationNotification notification = new ValidationNotification();

        notification.addErrorIf(true, "Birth date is in the future");
        notification.addErrorIf(false, "Ignore this error");

        Assertions.assertEquals(1, notification.getErrors().size());
        Assertions.assertEquals("Birth date is in the future", notification.getErrors().get(0));
    }

    @Test
    @DisplayName("AddErrorIf should add error message when boolean is true")
    void addErrorIf_shouldAddError_whenBooleanSupplierTrue() {
        BooleanSupplier bool = () -> 5 > 2;
        String message = "mensagem erro";
        ValidationNotification notification = new ValidationNotification();

        notification.addErrorIf(bool, message);

        Assertions.assertNotNull(notification.getErrors());
        Assertions.assertFalse(notification.getErrors().isEmpty());
        Assertions.assertEquals(1, notification.getErrors().size());
    }

    @Test
    @DisplayName("AddErrorIf should not add error message when boolean is false")
    void addErrorIf_shouldNotAddError_whenBooleanSupplierFalse() {
        BooleanSupplier bool = () -> 5 < 2;
        String message = "mensagem erro";
        ValidationNotification notification = new ValidationNotification();

        notification.addErrorIf(bool, message);

        Assertions.assertNotNull(notification.getErrors());
        Assertions.assertTrue(notification.getErrors().isEmpty());
        Assertions.assertEquals(0, notification.getErrors().size());
    }

    @Test
    @DisplayName("AddErrorIf should not add error message when supplier is null")
    void addErrorIf_shouldNotAddError_whenBooleanSupplierNull() {
        BooleanSupplier bool = null;
        String message = "mensagem erro";
        ValidationNotification notification = new ValidationNotification();

        notification.addErrorIf(bool, message);

        Assertions.assertNotNull(notification.getErrors());
        Assertions.assertTrue(notification.getErrors().isEmpty());
        Assertions.assertEquals(0, notification.getErrors().size());
    }

    @Test
    @DisplayName("Append should append errors when notification param has errors")
    void append_shouldAppendErrors_whenNotificationParamHasErrors() {
        ValidationNotification notification = new ValidationNotification();
        notification.addError("Error");
        ValidationNotification notification2 = new ValidationNotification();
        notification2.addError("Error 2");

        notification.append(notification2);

        Assertions.assertTrue(notification.hasErrors());
        Assertions.assertEquals(2, notification.getErrors().size());
    }

    @Test
    @DisplayName("Append should not append when notification param has not errors")
    void append_shouldNotAppend_whenNotificationParamHasNoErrors() {
        ValidationNotification notification = new ValidationNotification();
        ValidationNotification notification2 = new ValidationNotification();

        notification.append(notification2);

        Assertions.assertFalse(notification.hasErrors());
    }

    @Test
    @DisplayName("Append should not append when notification param is null")
    void append_shouldNotAppend_whenNotificationParamIsNull() {
        ValidationNotification notification = new ValidationNotification();
        ValidationNotification notification2 = null;

        notification.append(notification2);

        Assertions.assertFalse(notification.hasErrors());
    }
}
