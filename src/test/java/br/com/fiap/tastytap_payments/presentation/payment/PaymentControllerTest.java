package br.com.fiap.tastytap_payments.presentation.payment;

import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentUseCase;
import br.com.fiap.tastytap_payments.presentation.payment.dtos.CreatePaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

class PaymentControllerTest {

    private PaymentController paymentController;
    private CreatePaymentUseCase createPaymentUseCase;
    private FindPaymentUseCase findPaymentUseCase;

    @BeforeEach
    void setUp() {
        findPaymentUseCase = mock(FindPaymentUseCase.class);
        createPaymentUseCase = mock(CreatePaymentUseCase.class);
        paymentController = new PaymentController(createPaymentUseCase, findPaymentUseCase);
    }

    @Test
    void create_shouldCreatePaymentSuccessfully() {
        CreatePaymentRequest request = new CreatePaymentRequest(124L, BigDecimal.TEN);
        CreatePaymentResponse response = new CreatePaymentResponse(LocalDateTime.now(), 123L, "http://qrcode.com", PENDING);
        when(createPaymentUseCase.execute(request)).thenReturn(Optional.of(response));

        ResponseEntity<CreatePaymentResponse> actualResponse = paymentController.create(request);

        assertThat(actualResponse.getStatusCode()).isEqualTo(CREATED);
        assertThat(actualResponse.getBody()).isNotNull();
        assertThat(response.transactionId()).isEqualTo(actualResponse.getBody().transactionId());
    }

    @Test
    void create_shouldReturnBadRequest() {
        CreatePaymentRequest request = new CreatePaymentRequest(124L, BigDecimal.TEN);
        when(createPaymentUseCase.execute(request)).thenReturn(Optional.empty());

        ResponseEntity<CreatePaymentResponse> response = paymentController.create(request);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void testFindPayment_PaymentFound() {
        Long transactionId = 1L;
        FindPaymentResponse paymentResponse = new FindPaymentResponse(transactionId, LocalDateTime.now(),
                PENDING, "http://qrcodeurl.com", 124L, BigDecimal.TEN);
        when(findPaymentUseCase.execute(transactionId)).thenReturn(Optional.of(paymentResponse));

        ResponseEntity<FindPaymentResponse> response = paymentController.find(transactionId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().transactionId()).isEqualTo(transactionId);
    }

    @Test
    void testFindPayment_PaymentNotFound() {
        Long transactionId = 1L;
        when(findPaymentUseCase.execute(transactionId)).thenReturn(Optional.empty());

        ResponseEntity<FindPaymentResponse> response = paymentController.find(transactionId);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

}