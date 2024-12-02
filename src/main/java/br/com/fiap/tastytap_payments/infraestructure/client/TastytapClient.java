package br.com.fiap.tastytap_payments.infraestructure.client;

import br.com.fiap.tastytap_payments.domain.Status;
import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.POST;

@Service
public class TastytapClient {

    @Value("${tastytap.webhook.url}")
    private String url;

    private final RestTemplate restTemplate;

    public TastytapClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notify(Long transactionId, Status status) {
        RequestEntity<PaymentNotificationRequest> request = buildRequest(transactionId, status);
        try {
            this.restTemplate.exchange(request, String.class);
        } catch (Exception e) {
            throw new ApiException("Error notify tastytap webhook with transaction_id: %s".formatted(transactionId));
        }
    }

    private RequestEntity<PaymentNotificationRequest> buildRequest(Long transactionId, Status status) {
        return new RequestEntity<>(new PaymentNotificationRequest(transactionId, status), POST, URI.create(url));
    }
}
