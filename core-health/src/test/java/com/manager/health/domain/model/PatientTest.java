package com.manager.health.domain.model;

import com.manager.shared.domain.enums.Country;
import com.manager.shared.domain.model.entity.Address;
import com.manager.shared.domain.model.entity.Document;
import com.manager.shared.domain.model.entity.Email;
import com.manager.shared.domain.model.entity.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PatientTest {

    @Test
    void shouldCreateValidPatient_withValidParameters() {
        LocalDate birthDate = LocalDate.now();
        Patient patient = createPatient("test", birthDate);

        Assertions.assertNotNull(patient);
        Assertions.assertEquals("test", patient.getName());
        Assertions.assertEquals(birthDate, patient.getBirthDate());
        Assertions.assertTrue(patient.isActive());

        Assertions.assertEquals("50663102154", patient.getDocument().getRawValue());
        Assertions.assertEquals("506.631.021-54", patient.getDocument().getFormatted());

        Assertions.assertEquals("5551999999999", patient.getPhone().getInternationalRaw());
        Assertions.assertEquals("+55 (51) 99999-9999", patient.getPhone().getInternationalFormatted());

        Assertions.assertEquals("asd@asd.com", patient.getEmail().getValue());
    }

    @Test
    void shouldCreateValidPatient_usingSetters_andValidateWithGetters() {
        LocalDate dataNascimento = LocalDate.now();

        Patient patient = createPatient("nome paciente", dataNascimento);
        patient.setName("Nome mudado");
        patient.setBirthDate(dataNascimento);
        patient.setDocument(new Document("651+255+780-88", Country.BRAZIL));
        patient.setEmail(new Email("asd.asd@gmail.com"));
        patient.setPhone(new Phone("51999995555", Country.BRAZIL));
        patient.setAddress(new Address("street", "1asd", "complement", "neighborhood", "city", "state", "95900-000", Country.BRAZIL));
        patient.setActive(false);

        Assertions.assertNotNull(patient);
        Assertions.assertEquals("Nome mudado", patient.getName(), "Nome deve ser igual ao setado");
        Assertions.assertEquals(dataNascimento, patient.getBirthDate(), "Data de nascimento deve ser igual ao setado");
        Assertions.assertEquals("65125578088", patient.getDocument().getRawValue(), "Formatador deve limpar e deixar somente números");
        Assertions.assertEquals("651.255.780-88", patient.getDocument().getFormatted(), "Formatador deve formatar documento conforme país");
        Assertions.assertEquals("asd.asd@gmail.com", patient.getEmail().getValue());
        Assertions.assertEquals("5551999995555", patient.getPhone().getInternationalRaw(), "Deve retornar númeração internacional do telefone sem formatação");
        Assertions.assertEquals("+55 (51) 99999-5555", patient.getPhone().getInternationalFormatted(), "Deve retornar númeração internacional do telefone com formatação");
        Assertions.assertEquals("STREET", patient.getAddress().getStreet(), "Deve retornar uppercase");
        Assertions.assertEquals("1", patient.getAddress().getNumber(), "Ao enviar qualquer coisa, deve retornar somente os números");
        Assertions.assertEquals("COMPLEMENT", patient.getAddress().getComplement(), "Deve retornar uppercase");
        Assertions.assertEquals("NEIGHBORHOOD", patient.getAddress().getNeighborhood(), "Deve retornar uppercase");
        Assertions.assertEquals("CITY", patient.getAddress().getCity(), "Deve retornar uppercase");
        Assertions.assertEquals("STATE", patient.getAddress().getState(), "Deve retornar uppercase");
        Assertions.assertEquals("95900000", patient.getAddress().getZipCode(), "Deve remover qualquer caractere não numérico");
        Assertions.assertEquals("BR", patient.getAddress().getCountry().getCodeAlpha2());
    }

    @Test
    void shouldNotCreatePatient_withNullName() {
        LocalDate birthDate = LocalDate.now();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> createPatient(null, birthDate));

        Assertions.assertEquals("Name cannot be blank or null!", exception.getMessage());
    }

    @Test
    void shouldNotCreatePatient_withBlankName() {
        LocalDate birthDate = LocalDate.now();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> createPatient("   ", birthDate));
        Assertions.assertEquals("Name cannot be blank or null!", exception.getMessage());
    }

    @Test
    void shouldNotCreatePatient_withNullBirthDate() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> createPatient("asd", null));
        Assertions.assertEquals("Birthdate cannot be null or in the future!", exception.getMessage());
    }

    @Test
    void shouldNotCreatePatient_withInvalidBirthDate() {
        LocalDate birthDate = LocalDate.now().plusDays(1).plusMonths(2).plusYears(2);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> createPatient("test", birthDate));
        Assertions.assertEquals("Birthdate cannot be null or in the future!", exception.getMessage());
    }

    private Patient createPatient(String name, LocalDate birthDate) {
        String docNumber = "50663102154";
        Country countryCode = Country.fromCodeOrDdi("BR");
        Document doc = new Document(docNumber, countryCode);
        Phone phone = new Phone("51999999999", countryCode);
        Email email = new Email("asd@asd.com");
        Address add = new Address("rua", "1", "complement", "neigh", "city", "RS", "95900106", countryCode);

        return new Patient(name, birthDate, doc, email, phone, add, true);
    }

}
