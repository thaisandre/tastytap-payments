package br.com.fiap.tastytap_payments.application.usecase.update;

import br.com.fiap.tastytap_payments.application.PaymentGateway;

public class DefaultUpdatePaymentStatusUseCase extends UpdatePaymentStatusUseCase {

    private final PaymentGateway paymentGateway;

    public DefaultUpdatePaymentStatusUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void execute(UpdatePaymentStatusCommand command) {
        paymentGateway.updateStatus(command.transactionId(), command.status());
    }
}
