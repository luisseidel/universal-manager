package com.manager.shared.domain.model.entity;

public class Email {

    private final String value;

    public Email(String value) {
        if (value == null || !value.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.value = value.toLowerCase();
    }

    public String getValue() { return value; }

}
