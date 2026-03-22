package com.manager.shared.domain.model.entity;

public class Email {

    private final String value;
    private static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public Email(String value) {
        if (value == null || !value.matches(REGEX_EMAIL)) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.value = value.toLowerCase();
    }

    public String getValue() { return value; }

}
