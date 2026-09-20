package com.manager.shared.domain.model.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrazilDocumentValidatorTest {

    private final BrazilDocumentValidator validator = new BrazilDocumentValidator();

    @Test
    @DisplayName("Should return empty string when clean argument is null")
    void clean_shouldReturnEmptyString_whenNullArgument() {
        String result = validator.clean(null);

        assertEquals("", result, "Clean with null argument should return an empty string");
    }

    @Test
    @DisplayName("Should strip invalid special characters and keep alphanumeric in uppercase")
    void clean_shouldStripSpecialCharactersAndUppercase_whenRawValueHasSymbols() {
        String cpfWithSymbols = "12345678900=asd";
        String cnpjWithSymbols = "asd12365as4d00=_-!@#";

        String cleanedCpf = validator.clean(cpfWithSymbols);
        String cleanedCnpj = validator.clean(cnpjWithSymbols);

        assertEquals("12345678900ASD", cleanedCpf);
        assertEquals("ASD12365AS4D00", cleanedCnpj);
    }

    @Test
    @DisplayName("Should return true when CPF or CNPJ is valid")
    void isValid_shouldReturnTrue_whenValidCpfOrCnpj() {
        String validCpf = "12345678909";
        String validCnpj = "02437430000109";
        String validAlphaCnpj = "M7K2JXWAYUX011";

        assertTrue(validator.isValid(validCpf), "Valid CPF should return true");
        assertTrue(validator.isValid(validCnpj), "Valid numeric CNPJ should return true");
        assertTrue(validator.isValid(validAlphaCnpj), "Valid alphanumeric CNPJ should return true");
    }

    @Test
    @DisplayName("Should return false when document has an unsupported length")
    void isValid_shouldReturnFalse_whenDocumentLengthIsInvalid() {
        String invalidLengthDoc = "CMCMCMASDA!@#!@#22231321213132123";

        assertFalse(validator.isValid(invalidLengthDoc), "Document with invalid length should return false");
    }

    @Test
    @DisplayName("Should return false when CPF or CNPJ checksum or digits are invalid")
    void isValid_shouldReturnFalse_whenChecksumOrRepeatedSequenceIsInvalid() {
        String invalidCpfChecksum = "123.456.789-31";
        String invalidCnpjChecksum = "02.437.430/0001-11";
        String invalidAlphaCnpjChecksum = "M7.K2J.XWA/YUX0-cc";
        String repeatedCpfSequence = "11111111111";

        assertFalse(validator.isValid(invalidCpfChecksum), "CPF with invalid verification digits should return false");
        assertFalse(validator.isValid(invalidCnpjChecksum), "CNPJ with invalid verification digits should return false");
        assertFalse(validator.isValid(invalidAlphaCnpjChecksum), "Alphanumeric CNPJ with invalid verification digits should return false");
        assertFalse(validator.isValid(repeatedCpfSequence), "CPF with repeated sequence digits should return false");
    }

    @Test
    @DisplayName("Should return false when cleaned CPF has 11 characters but fails regex syntax")
    void isValid_shouldReturnFalse_whenCleanedCpfHas11CharsWithLetters() {
        // "123.456.789-AB" becomes "123456789AB" (length 11, fails REGEX_CPF)
        String cpfWithLetters = "123.456.789-AB";

        assertFalse(validator.isValid(cpfWithLetters), "CPF with 11 characters containing letters should fail REGEX_CPF check");
    }

    @Test
    @DisplayName("Should return false when cleaned CNPJ has 14 characters but fails regex syntax or zeroed check")
    void isValid_shouldReturnFalse_whenCleanedCnpjHas14CharsWithInvalidPattern() {
        // "ABCDEFGHIJKLMN" (length 14, fails REGEX_CNPJ requiring 2 last numeric digits)
        String invalidAlphaPatternCnpj = "ABCDEFGHIJKLMN";
        String zeroedCnpj = "00000000000000";

        assertFalse(validator.isValid(invalidAlphaPatternCnpj), "CNPJ with 14 characters failing REGEX_CNPJ pattern should return false");
        assertFalse(validator.isValid(zeroedCnpj), "Zeroed CNPJ should return false");
    }

    @Test
    @DisplayName("Should return formatted string when document is valid")
    void format_shouldReturnFormattedDocument_whenValidCpfOrCnpj() {
        String validCpf = "12345678909";
        String validCnpj = "02437430000109";
        String validAlphaCnpj = "M7K2JXWAYUX011";

        assertEquals("123.456.789-09", validator.format(validCpf));
        assertEquals("02.437.430/0001-09", validator.format(validCnpj));
        assertEquals("M7.K2J.XWA/YUX0-11", validator.format(validAlphaCnpj));
    }

    @Test
    @DisplayName("Should return cleaned string unformatted when document has invalid format length")
    void format_shouldReturnCleanedUnformatted_whenDocumentLengthIsInvalid() {
        String rawCpf = "12345678909ASD!@";
        String rawCnpj = "02437430000109CC@@";
        String rawAlphaCnpj = "M7K2JXWAYUX011 cascc";

        assertEquals("12345678909ASD", validator.format(rawCpf));
        assertEquals("02437430000109CC", validator.format(rawCnpj));
        assertEquals("M7K2JXWAYUX011CASCC", validator.format(rawAlphaCnpj));
    }
}