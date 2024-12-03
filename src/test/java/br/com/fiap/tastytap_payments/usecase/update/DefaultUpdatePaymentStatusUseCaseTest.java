package br.com.fiap.tastytap_payments.usecase.update;

import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.application.usecase.update.DefaultUpdatePaymentStatusUseCase;
import br.com.fiap.tastytap_payments.application.usecase.update.UpdatePaymentStatusCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static org.mockito.Mockito.*;

class DefaultUpdatePaymentStatusUseCaseTest {

    private DefaultUpdatePaymentStatusUseCase defaultUpdatePaymentStatusUseCase;
    private PaymentGateway paymentGateway;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        defaultUpdatePaymentStatusUseCase = new DefaultUpdatePaymentStatusUseCase(paymentGateway);
    }

    @Test
    void execute__shouldUpdatePaymentStatus() {
        UpdatePaymentStatusCommand command = mock(UpdatePaymentStatusCommand.class);
        when(command.status()).thenReturn(PENDING);
        when(command.transactionId()).thenReturn(1234567L);
        defaultUpdatePaymentStatusUseCase.execute(command);
        verify(paymentGateway).updateStatus(command.transactionId(), command.status());
    }
}