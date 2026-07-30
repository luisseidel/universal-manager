package com.manager.shared.domain.model.validators;

public class USADocumentValidator implements IDocumentValidator {

    private static final String REGEX_NUMBER = "\\D";
    private static final String REGEX_DIGITS = "\\d{9}";
    private static final String DOCUMENT_MASK = "$1-$2-$3";
    private static final String REGEX_MASK = "(\\d{3})(\\d{2})(\\d{4})";

    @Override
    public boolean isValid(String value) {
        String cleaned = clean(value);

        if (cleaned == null || !cleaned.matches(REGEX_DIGITS)) {
            return false;
        }

        int area = Integer.parseInt(cleaned.substring(0, 3));
        int group = Integer.parseInt(cleaned.substring(3, 5));
        int serial = Integer.parseInt(cleaned.substring(5, 9));

        // Regras da SSA (Social Security Administration)
        return validateArea(area) && validateGroup(group) && validateSerial(serial);
    }

    @Override
    public String format(String value) {
        String c = clean(value);
        if (c.length() == 9) {
            // Máscara padrão: AAA-GG-SSSS
            return c.replaceAll(REGEX_MASK, DOCUMENT_MASK);
        }
        return c;
    }

    @Override
    public String clean(String value) {
        return value != null ? value.replaceAll(REGEX_NUMBER, "") : "";
    }

    private boolean validateArea(int area) {
        return !(area == 0 || area == 666 || area >= 900);
    }

    private boolean validateGroup(int group) {
        return group != 0;
    }

    private boolean validateSerial(int serial) {
        return serial != 0;
    }
}
