package com.manager.cli;

import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.usecase.RegisterPatient;
import com.manager.shared.domain.validation.DomainValidationException;

import java.util.Scanner;

public class MainConsole {

    private final RegisterPatient registerPatientUseCase;
    private final Scanner scanner;

    public MainConsole(RegisterPatient registerPatientUseCase) {
        this.registerPatientUseCase = registerPatientUseCase;
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
                case "2" -> System.out.println("Funcionalidade de listagem em breve...");
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
        System.out.println("[2] Listar Pacientes (Em breve)");
        System.out.println("[0] Sair");
        System.out.print("Selecione uma opção: ");
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
            String zipCode = readInput("CEP/ZipCode: ");
            String street = readInput("Rua/Logradouro: ");
            String number = readInput("Número: ");
            String complement = readInput("Complemento (opcional): ", "");
            String neighborhood = readInput("Bairro: ");
            String city = readInput("Cidade: ");
            String state = readInput("Estado/UF: ");
            String addressCountry = readInput("País do Endereço (BR/US): ", "BR");

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
                    addressCountry
            );

            // 6. Execução do Caso de Uso
            registerPatientUseCase.execute(request);
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