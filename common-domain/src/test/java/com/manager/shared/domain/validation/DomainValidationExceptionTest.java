package com.manager.shared.domain.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class DomainValidationExceptionTest {

    @Test
    @DisplayName("Should instantiate exception and preserve single error message")
    void constructor_shouldPreserveMessage_whenSingleMessageProvided() {
        String errorMessage = "Invalid CPF provided";

        DomainValidationException exception = new DomainValidationException(errorMessage);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(1, exception.getErrors().size());
        Assertions.assertEquals(errorMessage, exception.getErrors().get(0));
    }

    @Test
    @DisplayName("Should instantiate exception from ValidationNotification with all accumulated errors")
    void constructor_shouldPreserveAllErrors_whenInstantiatedFromValidationNotification() {
        ValidationNotification notification = new ValidationNotification();
        notification.addError("Patient name is required");
        notification.addError("Birth date cannot be in the future");

        DomainValidationException exception = new DomainValidationException(notification);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(2, exception.getErrors().size());
        Assertions.assertTrue(exception.getMessage().contains("Patient name is required"));
        Assertions.assertTrue(exception.getMessage().contains("Birth date cannot be in the future"));
    }

    @Test
    @DisplayName("Should ensure class inherits from RuntimeException")
    void class_shouldBeSubclassOfRuntimeException_always() {
        DomainValidationException exception = new DomainValidationException("Validation error");

        Assertions.assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Constuctor should copy error list when list is not null")
    void constructor_shouldCopyErrorList_whenListNotNull() {
        String mainMessage = "main message error";
        List<String> errors = List.of("error 1", "error 2");

        DomainValidationException ex = new DomainValidationException(mainMessage, errors);

        Assertions.assertNotNull(ex);
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertFalse(ex.getErrors().isEmpty());
        Assertions.assertEquals(2, ex.getErrors().size());
        Assertions.assertEquals(mainMessage, ex.getMessage());
    }

    @Test
    @DisplayName("Constructor should return empty list when null erros list")
    void constructor_shouldReturnEmptyList_whenNullErrorList() {
        String mainMessage = "main message error";
        List<String> errors = null;

        DomainValidationException ex = new DomainValidationException(mainMessage, errors);

        Assertions.assertNotNull(ex);
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertTrue(ex.getErrors().isEmpty());
        Assertions.assertEquals(mainMessage, ex.getMessage());
    }
}
