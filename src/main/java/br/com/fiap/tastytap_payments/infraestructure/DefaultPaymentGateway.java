package br.com.fiap.tastytap_payments.infraestructure;

import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.application.usecase.create.QRCodeView;
import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;
import br.com.fiap.tastytap_payments.infraestructure.client.ProviderPaymentClient;
import br.com.fiap.tastytap_payments.infraestructure.client.QRCodeResponse;
import br.com.fiap.tastytap_payments.infraestructure.client.TastytapClient;
import br.com.fiap.tastytap_payments.infraestructure.persistence.PaymentEntity;
import br.com.fiap.tastytap_payments.infraestructure.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DefaultPaymentGateway implements PaymentGateway {

    private final ProviderPaymentClient providerClient;
    private final PaymentRepository paymentRepository;
    private final TastytapClient tastytapClient;

    public DefaultPaymentGateway(ProviderPaymentClient paymentClient, PaymentRepository paymentRepository, TastytapClient tastytapClient) {
        this.providerClient = paymentClient;
        this.paymentRepository = paymentRepository;
        this.tastytapClient = tastytapClient;
    }

    @Override
    public Payment persist(Payment payment) {
        return paymentRepository.save(new PaymentEntity(payment)).toDomain();
    }

    @Override
    public void updateStatus(Long transactionId, Status status) {
        paymentRepository.update(transactionId, status);
        tastytapClient.notify(transactionId, status);
    }

    @Override
    public QRCodeView generateQRCode(Long referenceId, BigDecimal amount) {
        QRCodeResponse response = providerClient.generateQRCode(referenceId, amount);
        return new QRCodeView(response.transactionId(), response.qrCodeUrl());
    }

    @Override
    public Optional<Payment> findByTransactionId(Long transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .map(PaymentEntity::toDomain);
    }
}
