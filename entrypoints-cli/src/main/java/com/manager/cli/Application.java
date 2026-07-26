package com.manager.cli;

import application.AuditEventListener;
import com.manager.health.domain.repository.IPatientRepository;
import com.manager.health.service.PatientFacade;
import com.manager.infrastructure.gateways.SimpleEventBus;
import com.manager.infrastructure.gateways.ViaCepAddressLookupGateway;
import com.manager.storage.mem.InMemoryAuditRepository;
import com.manager.storage.mem.PatientRepository;
import domain.repository.IAuditRepository;

public class Application {

    static void main() {
        // 1. Infraestrutura comum
        SimpleEventBus eventBus = new SimpleEventBus();

        // 2. Módulo de Auditoria (Instanciado de forma independente)
        // Nota: O entrypoints-cli precisará da dependência do core-audit e infra-storage-mem no pom.xml
        IAuditRepository auditRepo = new InMemoryAuditRepository();
        AuditEventListener auditListener = new AuditEventListener(auditRepo);

        // Registra a auditoria no barramento de eventos
        eventBus.subscribe(auditListener::onEvent);

        // 3. Módulo de Saúde
        IPatientRepository patientRepository = new PatientRepository();

        // Passamos o eventBus para a Facade. Ela não sabe que a auditoria está ouvindo!
        PatientFacade patientFacade = new PatientFacade(patientRepository, eventBus);

        // 2. Instancia o Caso de Uso injetando o repositório
        var lookupGateway = new ViaCepAddressLookupGateway();

        // 3. Instancia a interface de usuário (CLI) injetando o caso de uso
        var console = new MainConsole(patientFacade, lookupGateway);

        // 4. Inicia o loop do sistema
        console.run();
    }


}
