package br.com.fiap.tastytap_payments.infraestructure.client;

import br.com.fiap.tastytap_payments.domain.Status;
import jakarta.validation.constraints.NotNull;

public record PaymentNotificationRequest(@NotNull Long transactionId, @NotNull Status status){}
