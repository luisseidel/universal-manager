package com.manager.shared.domain.validation;

import java.util.Collections;
import java.util.List;

public class DomainValidationException extends RuntimeException {

    private final List<String> errors;

    public DomainValidationException(String message) {
        super(message);
        this.errors = List.of(message);
    }

    public DomainValidationException(String mainMessage, List<String> errors) {
        super(mainMessage);
        this.errors = errors != null ? List.copyOf(errors) : Collections.emptyList();
    }

    public DomainValidationException(ValidationNotification notification) {
        super("Error in domain validation: " + String.join(", ", notification.getErrors()));
        this.errors = List.copyOf(notification.getErrors());
    }

    public List<String> getErrors() {
        return errors;
    }
}