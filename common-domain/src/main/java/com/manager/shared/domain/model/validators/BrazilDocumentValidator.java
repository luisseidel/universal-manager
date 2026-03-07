package com.manager.shared.domain.model.validators;

public class BrazilDocumentValidator implements IDocumentValidator {

    private static final String REGEX_VALOR_ZERADO = "^[0]+$";

    private static final String REGEX_ALFA = "[^a-zA-Z0-9]";

    private static final int VALOR_BASE = '0';

    private static final int[] CNPJ_WEIGHTS_DV = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
    private static final String REGEX_CNPJ = "^[A-Z0-9]{12}[0-9]{2}$";
    private static final String REGEX_FORMAT_CNPJ = "([A-Z0-9]{2})([A-Z0-9]{3})([A-Z0-9]{3})([A-Z0-9]{4})([0-9]{2})";

    private static final String REGEX_CPF = "^[A-Z0-9]{9}[0-9]{2}$";
    private static final String REGEX_CPF_REPEATED_DIGITS = "(\\w)\\1{10}";
    private static final String REGEX_FORMAT_CPF = "([A-Z0-9]{3})([A-Z0-9]{3})([A-Z0-9]{3})([0-9]{2})";

    @Override
    public boolean isValid(String value) {
        String cleaned = clean(value);
        if (cleaned.length() == 11) return isValidCpf(cleaned);
        if (cleaned.length() == 14) return isValidCnpj(cleaned);
        return false;
    }

    @Override
    public String format(String value) {
        String c = clean(value);
        if (c.length() == 11) {
            return c.replaceAll(REGEX_FORMAT_CPF, "$1.$2.$3-$4");
        }
        if (c.length() == 14) {
            return c.replaceAll(REGEX_FORMAT_CNPJ, "$1.$2.$3/$4-$5");
        }
        return c;
    }

    @Override
    public String clean(String value) {
        return value != null ? value.replaceAll(REGEX_ALFA, "").toUpperCase() : "";
    }

    private boolean isValidCpf(String cpf) {
        // 1. Validar formato básico e evitar sequências óbvias (ex: 11111111111)
        if (!cpf.matches(REGEX_CPF) || cpf.matches(REGEX_CPF_REPEATED_DIGITS)) return false;

        String base = cpf.substring(0, 9);
        int d1 = calculateCpfDigit(base, 10);
        int d2 = calculateCpfDigit(base + d1, 11);

        return cpf.endsWith("" + d1 + d2);
    }

    private int calculateCpfDigit(String str, int weight) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += Character.getNumericValue(c) * weight--;
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    private boolean isValidCnpj(String cnpj) {
        if (!cnpj.matches(REGEX_CNPJ) || cnpj.matches(REGEX_VALOR_ZERADO)) return false;

        String base = cnpj.substring(0, 12);
        int d1 = calculateCnpjDigit(base);
        int d2 = calculateCnpjDigit(base + d1);

        return cnpj.endsWith("" + d1 + d2);
    }

    private int calculateCnpjDigit(String cnpj) {
        int sum = 0;
        for (int i = cnpj.length() - 1; i >= 0; i--) {
            int valueChar = (int)cnpj.charAt(i) - VALOR_BASE;
            sum += valueChar * CNPJ_WEIGHTS_DV[CNPJ_WEIGHTS_DV.length - cnpj.length() + i];
        }
        return sum % 11 < 2 ? 0 : 11 - (sum % 11);
    }

}
