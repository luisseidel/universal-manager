package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;

import java.util.Map;

public class DocumentValidatorFactory {

    private DocumentValidatorFactory() {}

    private static final Map<Country, IDocumentValidator> VALIDATORS = Map.of(
            Country.BRAZIL, new BrazilDocumentValidator(),
            Country.UNITED_STATES, new USADocumentValidator()
    );

    public static IDocumentValidator getValidator(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("O país para validação não pode ser nulo.");
        }

        IDocumentValidator validator = VALIDATORS.get(country);

        if (validator == null) {
            throw new IllegalArgumentException("Validador de documento não implementado para o país: " + country.getFullName());
        }

        return validator;
    }

}
