package br.com.fiap.tastytap_payments.infraestructure;

import br.com.fiap.tastytap_payments.application.usecase.create.QRCodeView;
import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;
import br.com.fiap.tastytap_payments.infraestructure.client.ProviderPaymentClient;
import br.com.fiap.tastytap_payments.infraestructure.client.QRCodeResponse;
import br.com.fiap.tastytap_payments.infraestructure.client.TastytapClient;
import br.com.fiap.tastytap_payments.infraestructure.persistence.PaymentEntity;
import br.com.fiap.tastytap_payments.infraestructure.persistence.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultPaymentGatewayTest {

    private DefaultPaymentGateway defaultPaymentGateway;
    private ProviderPaymentClient providerClient;
    private PaymentRepository paymentRepository;
    private TastytapClient tastytapClient;

    @BeforeEach
    void setUp() {
        providerClient = mock(ProviderPaymentClient.class);
        paymentRepository= mock(PaymentRepository.class);
        tastytapClient = mock(TastytapClient.class);
        defaultPaymentGateway = new DefaultPaymentGateway(providerClient, paymentRepository, tastytapClient);
    }

    @Test
    void persist_shouldPersistPayment() {
        Payment payment = new Payment("id", LocalDateTime.now(), PENDING, 123L,
                "http://qrcode.com", 124L, BigDecimal.TEN);
        PaymentEntity paymentEntity = new PaymentEntity(payment);

        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        Payment persistedPayment = defaultPaymentGateway.persist(payment);
        assertThat(persistedPayment).isNotNull();
        verify(paymentRepository).save(any());
    }

    @Test
    void updateStatus__shouldUpdatePaymentStatusAndNotifyTastytapClient() {
        defaultPaymentGateway.updateStatus(123L, Status.APPROVED);
        verify(paymentRepository).update(any(), any());
        verify(tastytapClient).notify(any(), any());
    }

    @Test
    void generateQRCode__shouldGenerateQRCode() {
        QRCodeResponse qrCodeResponse = new QRCodeResponse(123L, "http://qrcode.com");
        when(providerClient.generateQRCode(124L, BigDecimal.TEN))
                .thenReturn(qrCodeResponse);

        QRCodeView qrCodeView = defaultPaymentGateway.generateQRCode(124L, BigDecimal.TEN);

        assertThat(qrCodeView.transactionId()).isEqualTo(qrCodeResponse.transactionId());
        assertThat(qrCodeView.qrCodeUrl()).isEqualTo(qrCodeResponse.qrCodeUrl());
        verify(providerClient).generateQRCode(any(), any());
    }

    @Test
    void findByTransactionId__shouldReturnPaymentByTransactionId() {
        Payment payment = new Payment("id", LocalDateTime.now(), PENDING, 123L,
                "http://qrcode.com", 124L, BigDecimal.TEN);
        PaymentEntity paymentEntity = new PaymentEntity(payment);

        when(paymentRepository.findByTransactionId(123L))
                .thenReturn(Optional.of(paymentEntity));

        Optional<Payment> possiblePayment = defaultPaymentGateway.findByTransactionId(123L);
        assertThat(possiblePayment).isPresent();
        assertThat(possiblePayment.get().getId()).isEqualTo(payment.getId());
    }

}