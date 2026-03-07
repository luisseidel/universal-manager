package com.manager.shared.domain.model.validators;

public interface IPhoneValidator {

    boolean isValid(String phoneNumber);
    String format(String phoneNumber);
    String clean(String phoneNumber);

}
