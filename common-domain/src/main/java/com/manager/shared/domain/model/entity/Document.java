package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.validators.DocumentValidatorFactory;
import com.manager.shared.domain.model.validators.IDocumentValidator;

import java.util.Objects;

public class Document {

    private final String value;
    private final Country country;
    private final IDocumentValidator validator;

    public Document(String value, Country country) {
        this.country = Objects.requireNonNull(country, "O país do documento é obrigatório");
        this.validator = DocumentValidatorFactory.getValidator(country);
        if (!validator.isValid(value)) {
            throw new IllegalArgumentException("Documento inválido");
        }
        this.value = validator.clean(value);
    }

    public Country getCountry() {
        return country;
    }

    public String getRawValue() { return value; }

    public String getFormatted() {
        return validator.format(this.value);
    }

}
