package br.com.fiap.tastytap_payments.application.usecase.find;

import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FindPaymentResponse(
        Long transactionId,
        LocalDateTime createdAt,
        Status status,
        String qrCodeUrl,
        Long externalReference,
        BigDecimal amount
) {

    public FindPaymentResponse(Payment payment) {
        this(payment.getTransactionId(), payment.getCreatedAt(), payment.getStatus(),
                payment.getQrCodeUrl(), payment.getExternalReference(), payment.getAmount());
    }
}
