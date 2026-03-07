package com.manager.shared.domain.model.validators;

public class USAPhoneValidator implements IPhoneValidator {

    @Override
    public boolean isValid(String phoneNumber) {
        String cleaned = clean(phoneNumber);

        // Deve ter exatamente 10 dígitos
        if (cleaned.length() != 10) {
            return false;
        }

        // Regra NANP: O primeiro dígito do código de área e do prefixo não pode ser 0 ou 1
        // Formato: [2-9]XX [2-9]XX XXXX
        return cleaned.matches("^[2-9][0-9]{2}[2-9][0-9]{6}$");
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
            return c.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3");
        }
        return c;
    }

}
