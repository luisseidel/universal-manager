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

        Assertions.assertEquals("asd@asd.com", patient.getAddress().getFullAddress());
    }

    @Test
    void shouldCreateValidPatient_usingSetters_andValidateWithGetters() {
        Patient patient = createPatient("nome paciente", LocalDate.now());

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
