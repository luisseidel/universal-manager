package com.manager.health.usecase;

import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.fakes.FakePatientRepository;
import com.manager.health.mapper.PatientMapper;
import com.manager.infrastructure.gateways.SimpleEventBus;
import com.manager.shared.events.IDomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterPatientTest {

    private IPatientRepository patientRepository;
    private PatientMapper patientMapper;
    private SimpleEventBus simpleEventBus;
    private RegisterPatient useCase;

    @BeforeEach
    void setUp() {
        this.patientRepository = new FakePatientRepository();
        this.patientMapper = new PatientMapper();
        this.simpleEventBus = new SimpleEventBus();
        this.useCase = new RegisterPatient(patientRepository, patientMapper, simpleEventBus);
    }

    @Test
    @DisplayName("Deve registrar um paciente com sucesso e persistir no repositório")
    void shouldRegisterPatientSuccessfully() {
        // Arrange
        String validCpf = "54280504031"; // Documento de teste
        RegisterPatientRequest request = createValidRegisterRequest(validCpf);

        // Act
        useCase.execute(request);

        // Assert
        var savedPatient = patientRepository.findByDocument(validCpf);
        assertTrue(savedPatient.isPresent(), "O paciente deveria ser salvo no repositório");
        assertEquals("Paciente Teste Unitario", savedPatient.get().getName());
        assertEquals(validCpf, savedPatient.get().getDocument());
    }

    @Test
    @DisplayName("Deve publicar o evento PATIENT_CREATED no barramento ao registrar um paciente")
    void shouldPublishPatientCreatedEvent() {
        // Arrange
        List<IDomainEvent> publishedEvents = new ArrayList<>();
        // "Espiamos" o SimpleEventBus subscrevendo uma lista local
        simpleEventBus.subscribe(publishedEvents::add);

        String validCpf = "98765432100";
        RegisterPatientRequest request = createValidRegisterRequest(validCpf);

        // Act
        useCase.execute(request);

        // Assert
        assertEquals(1, publishedEvents.size(), "Deveria ter disparado exatamente 1 evento");

        IDomainEvent event = publishedEvents.get(0);
        assertEquals("PATIENT_CREATED", event.eventType(), "O tipo do evento deve ser PATIENT_CREATED");
    }

    @Test
    @DisplayName("Deve falhar ao tentar registrar paciente com documento inválido")
    void shouldFailWhenDocumentIsInvalid() {
        // Arrange (CPF propositalmente inválido para acionar o BrazilDocumentValidator)
        String invalidCpf = "123";
        RegisterPatientRequest request = createValidRegisterRequest(invalidCpf);

        // Act & Assert
        assertThrows(Exception.class, () -> {
            useCase.execute(request);
        }, "Deveria lançar exceção de validação ao receber documento inválido");

        // Garante que NADA foi salvo no banco
        assertTrue(patientRepository.findByDocument(invalidCpf).isEmpty());
    }

    // Método auxiliar para criar DTOs válidos de maneira limpa nos testes
    private RegisterPatientRequest createValidRegisterRequest(String document) {
        return new RegisterPatientRequest(
                "Paciente Teste Unitario",
                "2000-12-12",
                document,
                "BR",
                "teste@email.com",
                "55",
                "51999999999",
                "Rua dos Testes",
                "123",
                "",
                "Bairro Teste",
                "Cidade Teste",
                "RS",
                "90000000",
                "BR"
        );
    }
}
