package com.manager.shared.domain.model.entity;

import com.manager.shared.domain.enums.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void constructor_shouldCreate_withValidBrazilianParameters() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = "95900000";
        Country country = Country.BRAZIL;

        Address address = new Address(street, number, complement, neighborhood, city, state, zip, country);
        String fullAddress = String.format("%s, %s - %s, %s/%s - %s", address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState(), address.getZipCode());

        Assertions.assertNotNull(address);
        Assertions.assertEquals(street.toUpperCase(), address.getStreet());
        Assertions.assertEquals(number, address.getNumber());
        Assertions.assertEquals(complement.toUpperCase(), address.getComplement());
        Assertions.assertEquals(neighborhood.toUpperCase(), address.getNeighborhood());
        Assertions.assertEquals(city.toUpperCase(), address.getCity());
        Assertions.assertEquals(state.toUpperCase(), address.getState());
        Assertions.assertEquals(zip, address.getZipCode());
        Assertions.assertEquals(country, address.getCountry());
        Assertions.assertEquals(fullAddress, address.getFullAddress());
    }

    @Test
    void constructor_shouldCreate_withValidUSParameters() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = "60601";
        Country country = Country.UNITED_STATES;

        Address address = new Address(street, number, complement, neighborhood, city, state, zip, country);
        String fullAddress = String.format("%s, %s - %s, %s/%s - %s", address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState(), address.getZipCode());

        Assertions.assertNotNull(address);
        Assertions.assertEquals(street.toUpperCase(), address.getStreet());
        Assertions.assertEquals(number, address.getNumber());
        Assertions.assertEquals(complement.toUpperCase(), address.getComplement());
        Assertions.assertEquals(neighborhood.toUpperCase(), address.getNeighborhood());
        Assertions.assertEquals(city.toUpperCase(), address.getCity());
        Assertions.assertEquals(state.toUpperCase(), address.getState());
        Assertions.assertEquals(zip, address.getZipCode());
        Assertions.assertEquals(country, address.getCountry());
        Assertions.assertEquals(fullAddress, address.getFullAddress());
    }

    @Test
    void constructor_shouldThrowNullPointer_withNullCountry() {
        String street = null;
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = null;
        String state = null;
        String zip = null;
        Country country = null;

        NullPointerException exception = Assertions.assertThrows(
            NullPointerException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("O país do documento é obrigatório", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullStreet() {
        String street = null;
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = "95900000";
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Campos obrigatórios do endereço não podem estar vazios.", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullCity() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = null;
        String state = "state";
        String zip = "95900000";
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Campos obrigatórios do endereço não podem estar vazios.", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullState() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = null;
        String zip = "95900000";
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Campos obrigatórios do endereço não podem estar vazios.", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withNullZip() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = null;
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Campos obrigatórios do endereço não podem estar vazios.", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withWrongBRZipCode() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = "959000012331230";
        Country country = Country.BRAZIL;

        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("CEP brasileiro deve conter 8 dígitos.", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowIllegalArgument_withWrongUSZipCode() {
        String street = "street";
        String number = "1";
        String complement = "complement";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        String zip = "9590";
        Country country = Country.UNITED_STATES;

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Address(street, number, complement, neighborhood, city, state, zip, country)
        );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("ZIP Code americano inválido.", exception.getMessage());
    }

}
