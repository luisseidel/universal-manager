package com.manager.cli;

import com.manager.health.domain.model.Patient;
import com.manager.health.domain.repository.IPatientIRepository;
import com.manager.health.service.IPatientFacade;
import com.manager.health.service.PatientFacade;
import com.manager.infrastructure.gateways.ViaCepAddressLookupGateway;
import com.manager.shared.domain.model.entity.Address;
import com.manager.shared.domain.model.entity.Document;
import com.manager.shared.domain.model.entity.Email;
import com.manager.shared.domain.model.entity.Phone;
import com.manager.shared.domain.model.validators.DocumentValidatorFactory;
import com.manager.storage.mem.PatientIRepository;

import java.time.LocalDate;

public class Application {

    static void main() {
        // 1. Instancia a Infraestrutura (Poderia ser um banco real aqui)
        var repository = new PatientIRepository();

        // 2. Instancia o Caso de Uso injetando o repositório
        var lookupGateway = new ViaCepAddressLookupGateway();

        IPatientFacade patientFacade = new PatientFacade(repository);

        seedData(repository);

        // 3. Instancia a interface de usuário (CLI) injetando o caso de uso
        var console = new MainConsole(patientFacade, lookupGateway);

        // 4. Inicia o loop do sistema
        console.run();
    }

    private static void seedData(IPatientIRepository repository) {
        System.out.println("🌱 Gerando massa de dados para teste...");
        for (int i = 1; i <= 25; i++) {
            String name = "Paciente Teste " + i;
            System.out.println(name);

            Patient p = new Patient(
                    name,
                    LocalDate.of(2000, 12, 12),
                    new Document("54280504032", "BR", DocumentValidatorFactory.getValidator("BR")),
                    new Email("paciente" + i + "@email.com"),
                    new Phone("55", "519999999" + i),
                    new Address("Rua " + i, String.valueOf(i), "", "Bairro", "Cidade", "ST", "12345000", "BR"),
                    (i % 2 == 0 ? true : false)
            );

            repository.save(p);
        }
        System.out.println("✅ 25 pacientes gerados.");
    }

}
