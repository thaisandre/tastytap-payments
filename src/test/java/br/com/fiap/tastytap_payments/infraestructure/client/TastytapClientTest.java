package br.com.fiap.tastytap_payments.infraestructure.client;

import br.com.fiap.tastytap_payments.domain.Status;
import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TastytapClientTest {

    private TastytapClient tastytapClient;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        tastytapClient = new TastytapClient(restTemplate);
        ReflectionTestUtils.setField(tastytapClient, "url", "http://tastytap.com");
    }

    @Test
    void notify_shouldThrowApiException_whenApiRequestThrowsException() {
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenThrow(new RuntimeException("custom error message"));

        assertThatThrownBy(() -> tastytapClient.notify(123L, Status.APPROVED))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Error notify tastytap webhook with transaction_id: 123");
    }

    @Test
    void notify_shouldNotThrowException_whenResponseStatusIsSuccess() {
        ResponseEntity response = mock(ResponseEntity.class);
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(200));
        when(response.getBody()).thenReturn(new PaymentNotificationRequest(123L, Status.APPROVED));

        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenReturn(response);

        tastytapClient.notify(123L, Status.APPROVED);
    }
}