package com.manager.cli;

import com.manager.health.usecase.RegisterPatient;
import com.manager.storage.mem.PatientRepository;

public class Application {

     static void main() {
        // 1. Instancia a Infraestrutura (Poderia ser um banco real aqui)
        var repository = new PatientRepository();

        // 2. Instancia o Caso de Uso injetando o repositório
        var registerPatientUseCase = new RegisterPatient(repository);

        // 3. Instancia a interface de usuário (CLI) injetando o caso de uso
        var console = new MainConsole(registerPatientUseCase);

        // 4. Inicia o loop do sistema
        console.run();
    }

}
