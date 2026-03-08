package com.manager.shared.domain.model;

public record AddressDTO(
        String street, String neighborhood, String city, String state, boolean found
) {
}
