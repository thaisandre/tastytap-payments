package br.com.fiap.tastytap_payments.application.usecase.create;


import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;

import java.time.LocalDateTime;

public record CreatePaymentResponse(LocalDateTime createdAt, Long transactionId, String qrCodeUrl, Status status) {

    public CreatePaymentResponse(Payment payment) {
        this(payment.getCreatedAt(), payment.getTransactionId(), payment.getQrCodeUrl(), payment.getStatus());
    }
}
