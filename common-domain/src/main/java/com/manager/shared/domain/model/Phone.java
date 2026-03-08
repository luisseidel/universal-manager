package com.manager.shared.domain.model;

import com.manager.shared.domain.model.validators.IPhoneValidator;
import com.manager.shared.domain.model.validators.PhoneValidatorFactory;

public class Phone {

    private final String countryCode;
    private final String rawNumber;
    private final IPhoneValidator validator;

    public Phone(String countryCode, String number) {
        this.validator = PhoneValidatorFactory.getValidator(countryCode);

        if (!validator.isValid(number)) {
            throw new IllegalArgumentException("Número de telefone inválido para o DDI +" + countryCode);
        }

        this.countryCode = countryCode;
        this.rawNumber = validator.clean(number);
    }

    public String getFormatted() {
        return "+" + countryCode + " " + validator.format(rawNumber);
    }

    public String getInternationalRaw() {
        return countryCode + rawNumber;
    }
}
