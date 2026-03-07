package com.manager.shared.domain.model.validators;

import java.util.Map;

public class PhoneValidatorFactory {

    private static final Map<String, IPhoneValidator> VALIDATORS = Map.of(
            "55", new BrazilPhoneValidator(),
            "1", new USAPhoneValidator()
    );

    public static IPhoneValidator getValidator(String countryCode) {
        IPhoneValidator validator = VALIDATORS.get(countryCode.toUpperCase());
        if (validator == null) {
            throw new IllegalArgumentException("Código do País não suportado: " + countryCode);
        }
        return validator;
    }

}
