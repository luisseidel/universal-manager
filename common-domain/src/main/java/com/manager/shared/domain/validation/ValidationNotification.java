package com.manager.shared.domain.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationNotification {

    private final List<String> errors = new ArrayList<>();

    public void addError(String message) {
        errors.add(message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getErrorMessage() {
        return String.join("\n", errors);
    }

}
