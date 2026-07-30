package com.manager.shared.domain.model.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrazilDocumentValidatorTest {

    private final BrazilDocumentValidator validator = new BrazilDocumentValidator();

    @Test
    @DisplayName("Deve validar e formatar um CPF numérico tradicional válido")
    void deveValidarCpfNumericoTradicional() {
        // 1. ARRANGE
        String cpfEntrada = "123.456.789-09";

        // 2. ACT
        boolean ehValido = validator.isValid(cpfEntrada);
        String formatado = validator.format(cpfEntrada);
        String limpo = validator.clean(cpfEntrada);

        // 3. ASSERT
        assertTrue(ehValido, "O CPF deveria ser considerado válido");
        assertEquals("123.456.789-09", formatado);
        assertEquals("12345678909", limpo);
    }

    @Test
    @DisplayName("Deve invalidar CPF com dígitos verificadores incorretos")
    void deveDetectarCpfComDigitoErrado() {
        // 1. ARRANGE
        String cpfIncorreto = "12345678900"; // Os dígitos corretos seriam 09

        // 2. ACT
        boolean ehValido = validator.isValid(cpfIncorreto);

        // 3. ASSERT
        assertFalse(ehValido, "O validador deveria rejeitar dígitos verificadores errados");
    }

    @Test
    @DisplayName("Deve invalidar sequências repetidas (Invariante de negócio)")
    void deveInvalidarSequenciasRepetidas() {
        // ARRANGE
        String sequencia = "11111111111";

        // ACT & ASSERT
        assertFalse(validator.isValid(sequencia), "Sequências repetidas não são CPFs reais");
    }

    //TESTES CNPJ

    @Test
    @DisplayName("Deve validar e formatar um CNPJ alfanumerico válido")
    void deveValidarCnpjAlfanumerico() {
        // 1. ARRANGE
        String cnpjEntrada = "M7.K2J.XWA/YUX0-11";

        // 2. ACT
        boolean ehValido = validator.isValid(cnpjEntrada);
        String formatado = validator.format(cnpjEntrada);
        String limpo = validator.clean(cnpjEntrada);

        // 3. ASSERT
        assertTrue(ehValido, "O CNPJ deveria ser considerado válido");
        assertEquals("M7.K2J.XWA/YUX0-11", formatado);
        assertEquals("M7K2JXWAYUX011", limpo);
    }

    @Test
    @DisplayName("Deve validar e formatar um CNPJ numérico tradicional válido")
    void deveValidarCnpjNumericoTradicional() {
        // 1. ARRANGE
        String cnpjEntrada = "02.437.430/0001-09";

        // 2. ACT
        boolean ehValido = validator.isValid(cnpjEntrada);
        String formatado = validator.format(cnpjEntrada);
        String limpo = validator.clean(cnpjEntrada);

        // 3. ASSERT
        assertTrue(ehValido, "O CNPJ deveria ser considerado válido");
        assertEquals("02.437.430/0001-09", formatado);
        assertEquals("02437430000109", limpo);
    }

    @Test
    @DisplayName("Deve invalidar CNPJ com dígitos verificadores incorretos")
    void deveDetectarCnpjComDigitoErrado() {
        // 1. ARRANGE
        String cnpjIncorreto = "92909389000112"; //o dv correto é 00

        // 2. ACT
        boolean ehValido = validator.isValid(cnpjIncorreto);

        // 3. ASSERT
        assertFalse(ehValido, "O validador deveria rejeitar dígitos verificadores errados");
    }
}
