package com.manager.shared.domain.model.validators;

public class BrazilPhoneValidator implements IPhoneValidator {


    @Override
    public boolean isValid(String phoneNumber) {
        String cleaned = clean(phoneNumber);
        return cleaned.matches("^[1-9]{2}[2-9][0-9]{7,8}$");
    }

    @Override
    public String format(String phoneNumber) {
        String c = clean(phoneNumber);
        if (c.length() == 11) {
            return c.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (c.length() == 10) {
            return c.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
        return c;
    }

    @Override
    public String clean(String phoneNumber) {
        return phoneNumber != null ? phoneNumber.replaceAll("[^0-9]", "") : "";
    }
}
