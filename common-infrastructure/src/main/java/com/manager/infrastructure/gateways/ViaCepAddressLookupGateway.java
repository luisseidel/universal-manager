package com.manager.infrastructure.gateways;

import com.manager.shared.domain.gateway.IAddressLookupGateway;
import com.manager.shared.domain.model.dto.AddressDTO;

public class ViaCepAddressLookupGateway implements IAddressLookupGateway {

    @Override
    public AddressDTO findByZipCode(String zipCode) {

        String cleanZip = zipCode.replaceAll("[^0-9]", "");
        if (cleanZip.length() != 8) return new AddressDTO("", "", "", "", false);

        try {
            var client = java.net.http.HttpClient.newHttpClient();
            var request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://viacep.com.br/ws/" + cleanZip + "/json/"))
                    .build();

            var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            if (body.contains("\"erro\": true")) return new AddressDTO("", "", "", "", false);

            // Extração simples de valores (em um projeto real, use Jackson ou Gson)
            return new AddressDTO(
                    extract(body, "logradouro"),
                    extract(body, "bairro"),
                    extract(body, "localidade"),
                    extract(body, "uf"),
                    true
            );
        } catch (Exception e) {
            return new AddressDTO("", "", "", "", false);
        }
    }

    private String extract(String json, String key) {
        String pattern = "\"" + key + "\": \"";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
