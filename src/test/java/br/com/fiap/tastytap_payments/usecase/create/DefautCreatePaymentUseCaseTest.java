package br.com.fiap.tastytap_payments.usecase.create;

import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentCommand;
import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.create.DefautCreatePaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.create.QRCodeView;
import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefautCreatePaymentUseCaseTest {

    private DefautCreatePaymentUseCase defautCreatePaymentUseCase;
    private PaymentGateway paymentGateway;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        defautCreatePaymentUseCase = new DefautCreatePaymentUseCase(paymentGateway);
    }

    @Test
    void execute__shouldThrowApiException_whenPaymentGatewayError() {
        CreatePaymentCommand command = mock(CreatePaymentCommand.class);
        when(command.amount()).thenReturn(BigDecimal.TEN);
        when(command.referenceId()).thenReturn(1L);

        ApiException apiException = new ApiException("Error generating QR Code from provider");
        when(paymentGateway.generateQRCode(command.referenceId(), command.amount()))
                .thenThrow(apiException);

        assertThatThrownBy(() -> defautCreatePaymentUseCase.execute(command))
            .isInstanceOf(ApiException.class)
            .hasMessageContaining(apiException.getMessage());
    }

    @Test
    void execute__shouldTReturnCreatedPayment_whenPaymentGatewaySuccess() {
        CreatePaymentCommand command = mock(CreatePaymentCommand.class);
        when(command.amount()).thenReturn(BigDecimal.TEN);
        when(command.referenceId()).thenReturn(1L);

        QRCodeView qrCodeView = mock(QRCodeView.class);
        when(qrCodeView.qrCodeUrl()).thenReturn("http://teste.com");
        when(qrCodeView.transactionId()).thenReturn(2L);

        Payment payment = mock(Payment.class);
        when(payment.getId()).thenReturn("12345");

        when(paymentGateway.generateQRCode(command.referenceId(), command.amount()))
                .thenReturn(qrCodeView);
        when(paymentGateway.persist(any())).thenReturn(payment);

        Optional<CreatePaymentResponse> response = defautCreatePaymentUseCase.execute(command);
        assertThat(response).isPresent();
    }
}