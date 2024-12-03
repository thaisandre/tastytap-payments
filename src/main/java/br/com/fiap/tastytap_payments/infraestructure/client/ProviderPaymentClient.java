package br.com.fiap.tastytap_payments.infraestructure.client;

import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpMethod.POST;

@Service
public class ProviderPaymentClient {

    @Value("${provider.url}")
    private String url;

    @Value("${provider.token}")
    private String token;

    private final RestTemplate restTemplate;

    public ProviderPaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public QRCodeResponse generateQRCode(Long referenceId, BigDecimal amount) {
        RequestEntity<QRCodeRequest> request = buildRequest(referenceId, amount);
        ResponseEntity<QRCodeResponse> response = this.restTemplate.exchange(request, QRCodeResponse.class);
        return Optional.of(response)
                .filter(r -> r.getStatusCode().is2xxSuccessful())
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new ApiException("Error generating QR Code from provider.",
                        response.getStatusCode().value()));
    }

    private RequestEntity<QRCodeRequest> buildRequest(Long referenceId, BigDecimal amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.token);
        return new RequestEntity<>(new QRCodeRequest(referenceId, amount), headers, POST, URI.create(url));
    }
}
