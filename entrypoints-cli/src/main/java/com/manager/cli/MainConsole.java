package com.manager.cli;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.service.IPatientFacade;
import com.manager.infrastructure.gateways.ViaCepAddressLookupGateway;
import com.manager.shared.domain.model.AddressDTO;
import com.manager.shared.domain.validation.DomainValidationException;

import java.util.List;
import java.util.Scanner;

public class MainConsole {

    private final IPatientFacade patientFacade;
    private final ViaCepAddressLookupGateway lookupGateway;
    private final Scanner scanner;

    public MainConsole(
            IPatientFacade patientFacade,
            ViaCepAddressLookupGateway lookupGateway
    ) {
        this.patientFacade = patientFacade;
        this.lookupGateway = lookupGateway;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        System.out.println("=== Sistema de Gestão de Saúde (CLI) ===");

        while (running) {
            displayMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> registerPatientFlow();
                case "2" -> showPatientsFlow();
                case "3" -> searchPatientsFlow();
                case "4" -> deletePatientFlow();
                case "0" -> {
                    running = false;
                    System.out.println("Encerrando o sistema...");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n[1] Cadastrar Novo Paciente");
        System.out.println("[2] Listar Pacientes");
        System.out.println("[3] Buscar Paciente");
        System.out.println("[4] Deletar Paciente");
        System.out.println("[0] Sair");
        System.out.print("Selecione uma opção: ");
    }

    private void showPatientsFlow() {
        List<PatientResponse> asd = patientFacade.listPatients();

        for (PatientResponse p : asd) {
            printPatient(p);
        }
    }

    private void printPatient(PatientResponse p) {
        System.out.println(
                "Nome: " + p.name() + System.lineSeparator() +
                        "Documento: " + p.document() + System.lineSeparator() +
                        "Endereço: " + p.address() + System.lineSeparator() +
                        "Email: " + p.email() + System.lineSeparator() +
                        "Fone: " + p.phone()
        );
    }

    private void searchPatientsFlow() {
        System.out.println("\n --- BUSCA DE PACIENTES ---");
        String docValue = readInput("Número do Documento (BR): ");
        PatientResponse response = patientFacade.findByDocument(docValue);

        printPatient(response);
    }

    private void deletePatientFlow() {
        System.out.println("\n --- EXCLUSÃO DE PACIENTE ---");
        String docValue = readInput("Número do Documento (BR): ");
        patientFacade.delete(docValue);
    }

    private void registerPatientFlow() {
        System.out.println("\n--- NOVO CADASTRO DE PACIENTE ---");

        try {
            // 1. Identificação
            String name = readInput("Nome completo: ");
            String birthDate = readInput("Data de Nascimento (AAAA-MM-DD): ");

            // 2. Documentação (Suporte BR/US)
            String docCountry = readInput("País do Documento (BR/US) [Padrão BR]: ", "BR");
            String docValue = readInput("Número do Documento (" + docCountry + "): ");

            // 3. Contato
            String email = readInput("E-mail: ");
            String ddi = readInput("DDI do Telefone (ex: 55): ");
            String phone = readInput("Telefone com DDD: ");

            // 4. Endereço
            System.out.println("\n--- ENDEREÇO ---");
            String zipCode = readInput("CEP: ");
            String country = readInput("País (BR/US) [BR]: ", "BR");

            String street, neighborhood, city, state;

            // Tenta busca automática se for Brasil
            AddressDTO autoAddress = null;
            if ("BR".equals(country)) {
                System.out.println("Buscando CEP...");
                autoAddress = lookupGateway.findByZipCode(zipCode);
            }

            if (autoAddress != null && autoAddress.found()) {
                System.out.println("✅ Endereço encontrado: " + autoAddress.street() + " - " + autoAddress.city());
                street = autoAddress.street();
                neighborhood = autoAddress.neighborhood();
                city = autoAddress.city();
                state = autoAddress.state();
            } else {
                if ("BR".equals(country)) System.out.println("⚠️ CEP não encontrado. Preencha manualmente.");
                street = readInput("Rua/Logradouro: ");
                neighborhood = readInput("Bairro: ");
                city = readInput("Cidade: ");
                state = readInput("Estado/UF: ");
            }

            String number = readInput("Número: ");
            String complement = readInput("Complemento (opcional): ", "");
            // 5. Montagem do DTO
            RegisterPatientRequest request = new RegisterPatientRequest(
                    name,
                    birthDate,
                    docValue,
                    docCountry,
                    email,
                    ddi,
                    phone,
                    street,
                    number,
                    complement,
                    neighborhood,
                    city,
                    state,
                    zipCode,
                    country
            );

            // 6. Execução do Caso de Uso
            patientFacade.registerPatient(request);
            System.out.println("\n✅ Paciente cadastrado com sucesso!");

        } catch (DomainValidationException e) {
            System.err.println("\n❌ Erro(s) encontrado(s) no cadastro:");
            System.err.println(e.getMessage());
            System.out.println("\nPor favor, tente realizar o cadastro novamente com os dados corrigidos.");
        }
    }

    /**
     * Auxiliar para leitura obrigatória
     */
    private String readInput(String label) {
        String input;
        do {
            System.out.print(label);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Este campo é obrigatório.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Auxiliar para leitura com valor padrão
     */
    private String readInput(String label, String defaultValue) {
        System.out.print(label);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
}