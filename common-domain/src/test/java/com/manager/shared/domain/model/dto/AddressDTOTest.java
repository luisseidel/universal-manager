package com.manager.shared.domain.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressDTOTest {

    @Test
    void constructor_shouldCreate_withValidParameters() {
        String street = "street";
        String neighborhood = "neighborhood";
        String city = "city";
        String state = "state";
        boolean found = false;

        AddressDTO addressDTO = new AddressDTO(street, neighborhood, city, state, found);

        Assertions.assertNotNull(addressDTO);
        Assertions.assertEquals(street, addressDTO.street());
        Assertions.assertEquals(neighborhood, addressDTO.neighborhood());
        Assertions.assertEquals(city, addressDTO.city());
        Assertions.assertEquals(state, addressDTO.state());
        Assertions.assertEquals(found, addressDTO.found());
    }

}
