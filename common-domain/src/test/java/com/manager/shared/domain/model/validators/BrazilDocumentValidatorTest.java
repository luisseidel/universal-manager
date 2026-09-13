package com.manager.shared.domain.model.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrazilDocumentValidatorTest {

    private final BrazilDocumentValidator validator = new BrazilDocumentValidator();

    @Test
    void clean_shouldReturnBlankString_whenNullArg() {
        String docNumber = null;

        String result = validator.clean(docNumber);

        Assertions.assertEquals("", result);
    }

    @Test
    void clean_shouldReturnAlphanumeric_whenInvalidCharacters() {
        String cpf = "12345678900=asd";
        String cnpj = "asd12365as4d00=_-!@#";

        String cleanedCPF = validator.clean(cpf);
        String cleanedCNPJ = validator.clean(cnpj);

        Assertions.assertEquals("12345678900ASD", cleanedCPF);
        Assertions.assertEquals("ASD12365AS4D00", cleanedCNPJ);
    }

    @Test
    void isValid_shouldReturnTrue_whenValidCpfCnpj() {
        String cpf = "12345678909";
        String cnpj = "02437430000109";
        String cnpjAlpha = "M7K2JXWAYUX011";

        boolean resultCpf = validator.isValid(cpf);
        boolean resultCnpj = validator.isValid(cnpj);
        boolean resultCnpjAlpha = validator.isValid(cnpjAlpha);

        Assertions.assertTrue(resultCpf);
        Assertions.assertTrue(resultCnpj);
        Assertions.assertTrue(resultCnpjAlpha);
    }

    @Test
    void isValid_shouldReturnFalse_whenInvalidDocNumber() {
        String docNumber = "CMCMCMASDA!@#!@#22231321213132123";

        boolean result = validator.isValid(docNumber);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Deve retornar false quando CPF limpo tem 11 caracteres mas contém letras")
    void isValid_shouldReturnFalse_whenCpfHas11CharsButContainsLetters() {
        String cpfWithLetters = "123.456.789-AB";

        boolean result = validator.isValid(cpfWithLetters);

        Assertions.assertFalse(result, "CPF com 11 caracteres contendo letras deve ser inválido");
    }

    @Test
    @DisplayName("Deve retornar false quando CNPJ limpo tem 14 caracteres mas formato é inválido")
    void isValid_shouldReturnFalse_whenCnpjHas14CharsWithInvalidPattern() {
        String cnpjInvalidPattern = "ABCDEFGHIJKLMN";

        boolean result = validator.isValid(cnpjInvalidPattern);

        Assertions.assertFalse(result, "CNPJ com 14 caracteres fora do padrão deve ser inválido");
    }

    @Test
    void isValid_shouldReturnFalse_whenInvalidCpfCnpj() {
        String cpf = "123.456.789-31";
        String cnpj = "02.437.430/0001-11";
        String cnpjZero = "00.000.000/0000-00";
        String cnpjAlpha = "M7.K2J.XWA/YUX0-cc";
        String sequence = "11111111111";

        boolean resultCpf = validator.isValid(cpf);
        boolean resultCnpj = validator.isValid(cnpj);
        boolean resultCnpjZero = validator.isValid(cnpjZero);
        boolean resultCnpjAlpha = validator.isValid(cnpjAlpha);
        boolean resultSequence = validator.isValid(sequence);

        Assertions.assertFalse(resultCpf);
        Assertions.assertFalse(resultCnpj);
        Assertions.assertFalse(resultCnpjZero);
        Assertions.assertFalse(resultCnpjAlpha);
        Assertions.assertFalse(resultSequence);
    }

    @Test
    void format_shouldReturnFormatted_whenValidCpfCnpj() {
        String cpf = "12345678909";
        String cnpj = "02437430000109";
        String cnpjAlpha = "M7K2JXWAYUX011";

        String cpfFormatted = validator.format(cpf);
        String cnpjFormatted = validator.format(cnpj);
        String cnpjAlphaFormatted = validator.format(cnpjAlpha);

        Assertions.assertEquals("123.456.789-09", cpfFormatted);
        Assertions.assertEquals("02.437.430/0001-09", cnpjFormatted);
        Assertions.assertEquals("M7.K2J.XWA/YUX0-11", cnpjAlphaFormatted);
    }

    @Test
    void format_shouldReturnCleaned_whenInvalidCpfCnpj() {
        String cpf = "12345678909ASD!@";
        String cnpj = "02437430000109CC@@";
        String cnpjAlpha = "M7K2JXWAYUX011 cascc";

        String cpfFormatted = validator.format(cpf);
        String cnpjFormatted = validator.format(cnpj);
        String cnpjAlphaFormatted = validator.format(cnpjAlpha);

        Assertions.assertEquals("12345678909ASD", cpfFormatted);
        Assertions.assertEquals("02437430000109CC", cnpjFormatted);
        Assertions.assertEquals("M7K2JXWAYUX011CASCC", cnpjAlphaFormatted);
    }
}
