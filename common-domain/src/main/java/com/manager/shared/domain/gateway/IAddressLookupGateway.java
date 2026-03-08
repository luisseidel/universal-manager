package com.manager.shared.domain.gateway;

import com.manager.shared.domain.model.AddressDTO;

public interface IAddressLookupGateway {
    AddressDTO findByZipCode(String zipCode);
}
