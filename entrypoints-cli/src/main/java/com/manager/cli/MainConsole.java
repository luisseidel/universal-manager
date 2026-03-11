package com.manager.cli;

import com.manager.health.domain.model.PatientResponse;
import com.manager.health.domain.model.RegisterPatientRequest;
import com.manager.health.service.IPatientFacade;
import com.manager.infrastructure.gateways.ViaCepAddressLookupGateway;
import com.manager.shared.domain.model.dto.AddressDTO;
import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.domain.validation.DomainValidationException;

import java.util.Scanner;

public class MainConsole {

    private final IPatientFacade patientFacade;
    private final ViaCepAddressLookupGateway addressLookupGateway;
    private final Scanner scanner;

    public MainConsole(
            IPatientFacade patientFacade,
            ViaCepAddressLookupGateway addressLookupGateway
    ) {
        this.patientFacade = patientFacade;
        this.addressLookupGateway = addressLookupGateway;
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
                case "5" -> updatePatientFlow();
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
        System.out.println("[5] Atualizar Paciente");
        System.out.println("[0] Sair");
        System.out.print("Selecione uma opção: ");
    }

    private void showPatientsFlow() {
        int currentPage = 1;
        int pageSize = 5;
        String option = "";

        while(!"V".equalsIgnoreCase(option)) {
            PagedResponse<PatientResponse> response = patientFacade.listPaged(currentPage, pageSize);

            System.out.println("\n--- LISTA DE PACIENTES (Página " + response.currentPage() + " de " + response.totalPages() + ") ---");
            System.out.println("Total de registros: " + response.totalItems());

            if (response.items().isEmpty()) {
                System.out.println("Nenhum paciente nesta página.");
            } else {
                response.items().forEach(this::printPatient);
            }

            System.out.println("\n[N] Próxima | [A] Anterior | [V] Voltar ao Menu");
            System.out.print("Escolha: ");
            option = scanner.nextLine().toUpperCase();

            if (option.equals("N") && response.hasNext()) {
                currentPage++;
            } else if (option.equals("A") && currentPage > 1) {
                currentPage--;
            } else if (!option.equals("V")) {
                System.out.println("⚠️ Opção inválida ou fim da lista.");
            }
        }

        PagedResponse<PatientResponse> asd = patientFacade.listPaged(10, 10);

        for (PatientResponse p : asd.items()) {
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
        System.out.println("-------------x-------------");
    }

    private void updatePatientFlow() {
        String document = readInput("Digite o número do documento do paciente: ");
        System.out.println("\n --- NOVOS DADOS ---");

        try {
            RegisterPatientRequest request = collectPatientData("update");
            patientFacade.update(document, request);
            System.out.println("Atualização concluída!");
        } catch (DomainValidationException e) {
            System.err.println("\n❌ Erro na atualização:\n" + e.getMessage());
        }

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
            RegisterPatientRequest request = collectPatientData("register");

            // 6. Execução do Caso de Uso
            patientFacade.create(request);
            System.out.println("\n✅ Paciente cadastrado com sucesso!");

        } catch (DomainValidationException e) {
            System.err.println("\n❌ Erro(s) encontrado(s) no cadastro:");
            System.err.println(e.getMessage());
            System.out.println("\nPor favor, tente realizar o cadastro novamente com os dados corrigidos.");
        }
    }

    private RegisterPatientRequest collectPatientData(String operationType) {
        String birthDate = "";
        String docValue = "";
        String docCountry = "";

        if (!"update".equalsIgnoreCase(operationType)) {
            birthDate = readInput("Data de Nascimento (AAAA-MM-DD): ");
            docCountry = readInput("País do Doc (BR/US) [BR]: ", "BR");
            docValue = readInput("Número do Documento: ");
        }
        String name = readInput("Nome completo: ");
        String email = readInput("E-mail: ");
        String ddi = readInput("DDI [default BR: 55]: ", "55");
        String phone = readInput("Telefone com DDD: ");

        System.out.println("\n-- Endereço --");
        String zip = readInput("CEP/Zip: ");
        String country = readInput("País do Endereço (BR/US) [BR]: ", "BR");

        String street, neighborhood, city, state;
        AddressDTO auto = ("BR".equals(country)) ? addressLookupGateway.findByZipCode(zip) : null;

        if (auto != null && auto.found()) {
            System.out.println("📍 Endereço auto-preenchido: " + auto.street());
            street = auto.street();
            neighborhood = auto.neighborhood();
            city = auto.city();
            state = auto.state();
        } else {
            street = readInput("Rua: ");
            neighborhood = readInput("Bairro: ");
            city = readInput("Cidade: ");
            state = readInput("Estado: ");
        }
        String num = readInput("Número: ");
        String comp = readInput("Complemento: ", "");

        return new RegisterPatientRequest(name, birthDate, docValue, docCountry, email, ddi, phone,
                street, num, comp, neighborhood, city, state, zip, country);
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