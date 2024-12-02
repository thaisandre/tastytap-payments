package br.com.fiap.tastytap_payments.presentation.payment.dtos;

import br.com.fiap.tastytap_payments.application.usecase.update.UpdatePaymentStatusCommand;
import br.com.fiap.tastytap_payments.domain.Status;
import jakarta.validation.constraints.NotNull;

public record PaymentNotificationRequest(@NotNull Long transactionId, @NotNull Status status)
        implements UpdatePaymentStatusCommand {
}
