package com.manager.health.domain.model;

import com.manager.shared.domain.model.Address;
import com.manager.shared.domain.model.Document;
import com.manager.shared.domain.model.Email;
import com.manager.shared.domain.model.Phone;

import java.time.LocalDate;
import java.util.UUID;

public class Patient {

    private final UUID id;
    private String name;
    private LocalDate birthDate;
    private Document document;
    private Email email;
    private Phone phone;
    private Address address;

    public Patient(String name, LocalDate birthDate, Document document, Email email, Phone phone, Address address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
        this.email = email;
        this.phone = phone;
        this.address = address;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}