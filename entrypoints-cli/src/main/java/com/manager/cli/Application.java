package com.manager.cli;

import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.service.IPatientFacade;
import com.manager.health.service.PatientFacade;
import com.manager.infrastructure.gateways.ViaCepAddressLookupGateway;
import com.manager.storage.mem.PatientIPatientRepository;

public class Application {

    static void main() {
        // 1. Instancia a Infraestrutura (Poderia ser um banco real aqui)
        var repository = new PatientIPatientRepository();

        // 2. Instancia o Caso de Uso injetando o repositório
        var lookupGateway = new ViaCepAddressLookupGateway();

        IPatientFacade patientFacade = new PatientFacade(repository);

        seedData(patientFacade);

        // 3. Instancia a interface de usuário (CLI) injetando o caso de uso
        var console = new MainConsole(patientFacade, lookupGateway);

        // 4. Inicia o loop do sistema
        console.run();
    }

    private static void seedData(IPatientFacade facade) {
        System.out.println("🌱 Gerando massa de dados para teste...");
        for (int i = 1; i <= 25; i++) {
            String name = "Paciente Teste " + i;
            System.out.println(name);

            facade.create(
                    new RegisterPatientRequest(
                        name,
                        "2000-12-12",
                        "5428050403"+i,
                        "BR",
                        "paciente" + i + "@email.com",
                        "55",
                        "519999999" + i,
                        "Rua " + i,
                        String.valueOf(i),
                        "",
                        "Bairro",
                        "Cidade",
                        "ST",
                        "12345000",
                        "BR"
                    )
            );
        }
        System.out.println("✅ 25 pacientes gerados.");
    }

}
