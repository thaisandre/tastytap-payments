package br.com.fiap.tastytap_payments.presentation.payment.dtos;

import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest(@NotNull Long referenceId,
                                   @NotNull @Positive BigDecimal amount
) implements CreatePaymentCommand {}
