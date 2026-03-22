package com.manager.shared.domain.model.validators;

public class USAPhoneValidator implements IPhoneValidator {

    private static final String DEFAULT_PHONE_FORMAT = "($1) $2-$3";
    private static final String REGEX_PHONE = "^[2-9][0-9]{2}[2-9][0-9]{6}$";
    private static final String REGEX_FORMAT_PHONE ="(\\d{3})(\\d{3})(\\d{4})";

    @Override
    public boolean isValid(String phoneNumber) {
        String cleaned = clean(phoneNumber);

        // Deve ter exatamente 10 dígitos
        if (cleaned.length() != 10) {
            return false;
        }

        // Regra NANP: O primeiro dígito do código de área e do prefixo não pode ser 0 ou 1
        // Formato: [2-9]XX [2-9]XX XXXX
        return cleaned.matches(REGEX_PHONE);
    }

    @Override
    public String clean(String phoneNumber) {
        return phoneNumber != null ? phoneNumber.replaceAll("[^0-9]", "") : "";
    }

    @Override
    public String format(String phoneNumber) {
        String c = clean(phoneNumber);
        if (c.length() == 10) {
            // Formato padrão USA: (XXX) XXX-XXXX
            return c.replaceAll(REGEX_FORMAT_PHONE, DEFAULT_PHONE_FORMAT);
        }
        return c;
    }

}
