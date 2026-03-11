package com.manager.shared.domain.gateway;

import com.manager.shared.domain.model.dto.AddressDTO;

public interface IAddressLookupGateway {
    AddressDTO findByZipCode(String zipCode);
}
