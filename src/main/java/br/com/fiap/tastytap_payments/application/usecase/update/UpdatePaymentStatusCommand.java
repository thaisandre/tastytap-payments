package br.com.fiap.tastytap_payments.application.usecase.update;

import br.com.fiap.tastytap_payments.domain.Status;

public interface UpdatePaymentStatusCommand {
    Long transactionId();
    Status status();
}
