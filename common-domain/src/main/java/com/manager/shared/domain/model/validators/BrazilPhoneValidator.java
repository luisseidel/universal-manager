package com.manager.shared.domain.model.validators;

public class BrazilPhoneValidator implements IPhoneValidator {

    private static final String DEFAULT_PHONE_FORMAT = "($1) $2-$3";
    private static final String REGEX_PHONE = "^[1-9]{2}[2-9][0-9]{7,8}$";
    private static final String REGEX_CELL_PHONE = "(\\d{2})(\\d{5})(\\d{4})";
    private static final String REGEX_LANDLINE_PHONE ="(\\d{2})(\\d{4})(\\d{4})";

    @Override
    public boolean isValid(String phoneNumber) {
        String cleaned = clean(phoneNumber);
        return cleaned.matches(REGEX_PHONE);
    }

    @Override
    public String format(String phoneNumber) {
        String c = clean(phoneNumber);
        if (isMobilePhone(c)) {
            return c.replaceAll(REGEX_CELL_PHONE, DEFAULT_PHONE_FORMAT);
        } else if (isLandlineNumber(c)) {
            return c.replaceAll(REGEX_LANDLINE_PHONE, DEFAULT_PHONE_FORMAT);
        }
        return c;
    }

    @Override
    public String clean(String phoneNumber) {
        return phoneNumber != null ? phoneNumber.replaceAll("[^0-9]", "") : "";
    }

    private boolean isLandlineNumber(String number) {
        return number.length() == 10;
    }

    private boolean isMobilePhone(String number) {
        return number.length() == 11;
    }
}
