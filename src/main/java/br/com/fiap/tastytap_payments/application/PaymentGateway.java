package br.com.fiap.tastytap_payments.application;

import br.com.fiap.tastytap_payments.application.usecase.create.QRCodeView;
import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentGateway {
    Payment persist(Payment payment);
    void updateStatus(Long transactionId, Status status);
    QRCodeView generateQRCode(Long referenceId, BigDecimal amount);
    Optional<Payment> findByTransactionId(Long transactionId);
}
