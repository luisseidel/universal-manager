package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.validators.IPhoneValidator;
import com.manager.shared.domain.model.validators.PhoneValidatorFactory;

import java.util.Objects;

public class Phone {

    private final Country country;
    private final String rawNumber;
    private final IPhoneValidator validator;

    public Phone(String number, Country country) {
        this.country = Objects.requireNonNull(country, "O país do documento é obrigatório");
        this.validator = PhoneValidatorFactory.getValidator(country);

        if (!validator.isValid(number)) {
            throw new IllegalArgumentException("Número de telefone inválido para o DDI +" + country);
        }

        this.rawNumber = validator.clean(number);
    }

    public String getFormatted() {
        return validator.format(rawNumber);
    }

    public String getInternationalRaw() {
        return country.getPhoneDdi() + rawNumber;
    }

    public String getInternationalFormatted() {
        return "+" + country.getPhoneDdi() + " " + getFormatted();
    }
}
