package br.com.fiap.tastytap_payments.application.usecase.create;

import java.math.BigDecimal;

public interface CreatePaymentCommand {
    Long referenceId();
    BigDecimal amount();
}
