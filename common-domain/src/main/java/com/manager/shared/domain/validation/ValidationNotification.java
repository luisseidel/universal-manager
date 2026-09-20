package com.manager.shared.domain.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ValidationNotification {

    private final List<String> errors = new ArrayList<>();

    /**
     * Add error message direct to the list
     * @param message
     */
    public void addError(String message) {
        if (message != null && !message.trim().isBlank()) {
            this.errors.add(message);
        }
    }

    /**
     * Add error if the condition is true.
     * @param condition
     * @param message
     */
    public void addErrorIf(boolean condition, String message) {
        if (condition) {
            addError(message);
        }
    }

    /**
     * Add error if the suplier condition is true.
     * @param conditionSupplier
     * @param message
     */
    public void addErrorIf(BooleanSupplier conditionSupplier, String message) {
        if (conditionSupplier != null && conditionSupplier.getAsBoolean()) {
            addError(message);
        }
    }

    /**
     * Copy all errors from other notification. Useful to aggregate erros for VO's .
     * @param other
     */
    public void append(ValidationNotification other) {
        if (other != null && other.hasErrors()) {
            this.errors.addAll(other.getErrors());
        }
    }

    /**
     * Verify for accumulated errors
     * @return - true if exists and false otherwise
     */
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    /**
     * @return Returns a immutable list with all captured errors=
     */
    public List<String> getErrors() {
        return Collections.unmodifiableList(this.errors);
    }

}
