package br.com.fiap.tastytap_payments.infraestructure.client;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record QRCodeRequest(@NotNull Long referenceId, @NotNull BigDecimal amount) {
}
