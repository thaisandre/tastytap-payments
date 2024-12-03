package br.com.fiap.tastytap_payments.infraestructure.client;

import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProviderPaymentClientTest {

    private ProviderPaymentClient providerPaymentClient;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        providerPaymentClient = new ProviderPaymentClient(restTemplate);
        ReflectionTestUtils.setField(providerPaymentClient, "url", "http://teste.com");
        ReflectionTestUtils.setField(providerPaymentClient, "token", "test_token");
    }

    @Test
    void generateQRCode_shouldThrowException_whenResponseIsNotPresent() {
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenThrow(new RuntimeException("custom error message"));

        assertThatThrownBy(() -> providerPaymentClient.generateQRCode(1L, BigDecimal.TEN))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("custom error message");
    }

    @Test
    void generateQRCode_shouldThrowApiException_whenResponseStatusIsNotSuccess() {
        ResponseEntity response = mock(ResponseEntity.class);
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(400));
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenReturn(response);

        assertThatThrownBy(() -> providerPaymentClient.generateQRCode(1L, BigDecimal.TEN))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Error generating QR Code from provider.");
    }

    @Test
    void generateQRCode_shouldReturnResponse_whenResponseStatusIsSuccess() {
        ResponseEntity response = mock(ResponseEntity.class);
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(200));
        when(response.getBody()).thenReturn(new QRCodeResponse(123L, "http://qrcode.com"));

        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
                .thenReturn(response);

        QRCodeResponse qrCodeResponse = providerPaymentClient.generateQRCode(1L, BigDecimal.TEN);
        assertThat(qrCodeResponse).isNotNull();
        assertThat(qrCodeResponse.transactionId()).isEqualTo(123L);
        assertThat(qrCodeResponse.qrCodeUrl()).isEqualTo("http://qrcode.com");
    }
}