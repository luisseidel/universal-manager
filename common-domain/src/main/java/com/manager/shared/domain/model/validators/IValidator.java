package com.manager.shared.domain.model.validators;

public interface IValidator {

    boolean isValid(String value);
    String clean(String value);
    String format(String value);

}
