package br.com.fiap.tastytap_payments.usecase.find;

import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.application.usecase.find.DefaultFindPaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentResponse;
import br.com.fiap.tastytap_payments.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultFindPaymentUseCaseTest {

    private DefaultFindPaymentUseCase defaultFindPaymentUseCase;
    private PaymentGateway paymentGateway;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        defaultFindPaymentUseCase = new DefaultFindPaymentUseCase(paymentGateway);
    }

    @Test
    void execute__shouldReturnEmptyWhenNotExistsPaymentByTransactionId() {
        Long transactionId = 1L;
        when(paymentGateway.findByTransactionId(transactionId))
                .thenReturn(Optional.empty());
        Optional<FindPaymentResponse> response = defaultFindPaymentUseCase.execute(transactionId);
        assertThat(response).isEmpty();
    }

    @Test
    void execute__shouldFindByTransactionId() {
        Long transactionId = 1L;
        Payment payment = new Payment("id", now(), PENDING,transactionId,
                "content", 1L, BigDecimal.TEN);
        when(paymentGateway.findByTransactionId(transactionId))
                .thenReturn(Optional.of(payment));
        Optional<FindPaymentResponse> response = defaultFindPaymentUseCase.execute(transactionId);
        assertThat(response).isPresent();
        assertThat(response.get().transactionId()).isEqualTo(transactionId);
    }
}