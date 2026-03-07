package com.manager.shared.domain.model;

import com.manager.shared.domain.model.validators.IDocumentValidator;

public class Document {

    private final String value;
    private final String countryCode;
    private final IDocumentValidator validator;

    public Document(String value, String countryCode, IDocumentValidator validator) {
        if (!validator.isValid(value)) {
            throw new IllegalArgumentException("Documento inválido");
        }
        this.value = validator.clean(value);
        this.countryCode = countryCode;
        this.validator = validator;
    }

    public String getRawValue() { return value; }

    public String getFormatted() {
        return validator.format(this.value);
    }

}
