package com.manager.shared.domain.model.validators;

import java.util.Map;

public class DocumentValidatorFactory {

    private static final Map<String, IDocumentValidator> VALIDATORS = Map.of(
            "BR", new BrazilDocumentValidator(),
            "US", new USADocumentValidator()
    );

    public static IDocumentValidator getValidator(String countryCode) {
        IDocumentValidator validator = VALIDATORS.get(countryCode.toUpperCase());
        if (validator == null) {
            throw new IllegalArgumentException("País não suportado: " + countryCode);
        }
        return validator;
    }

}
