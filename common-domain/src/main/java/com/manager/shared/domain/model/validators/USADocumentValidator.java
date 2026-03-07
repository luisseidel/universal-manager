package com.manager.shared.domain.model.validators;

public class USADocumentValidator implements IDocumentValidator {

    @Override
    public boolean isValid(String value) {
        String cleaned = clean(value);

        // Deve ter exatamente 9 dígitos numéricos
        if (cleaned == null || !cleaned.matches("\\d{9}")) {
            return false;
        }

        int area = Integer.parseInt(cleaned.substring(0, 3));
        int group = Integer.parseInt(cleaned.substring(3, 5));
        int serial = Integer.parseInt(cleaned.substring(5, 9));

        // Regras da SSA (Social Security Administration)
        if (area == 0 || area == 666 || area >= 900) return false;
        if (group == 0) return false;
        if (serial == 0) return false;

        return true;
    }

    @Override
    public String format(String value) {
        String c = clean(value);
        if (c.length() == 9) {
            // Máscara padrão: AAA-GG-SSSS
            return c.replaceAll("(\\d{3})(\\d{2})(\\d{4})", "$1-$2-$3");
        }
        return c;
    }

    @Override
    public String clean(String value) {
        return value != null ? value.replaceAll("[^0-9]", "") : "";
    }
}
