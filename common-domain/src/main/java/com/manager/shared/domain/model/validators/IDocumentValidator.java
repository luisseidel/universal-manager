package com.manager.shared.domain.model.validators;

public interface IDocumentValidator {

    boolean isValid(String value);
    String clean(String value);
    String format(String value);

}
