package com.manager.shared.domain.model.validators;

import com.manager.shared.domain.enums.Country;

import java.util.Map;

public class PhoneValidatorFactory {

    private PhoneValidatorFactory() {}

    private static final Map<Country, IPhoneValidator> VALIDATORS = Map.of(
            Country.BRAZIL, new BrazilPhoneValidator(),
            Country.UNITED_STATES, new USAPhoneValidator()
    );

    public static IPhoneValidator getValidator(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("O país para validação não pode ser nulo.");
        }

        IPhoneValidator validator = VALIDATORS.get(country);

        if (validator == null) {
            throw new IllegalArgumentException("Validador de documento não implementado para o país: " + country.getFullName());
        }

        return validator;
    }

}
