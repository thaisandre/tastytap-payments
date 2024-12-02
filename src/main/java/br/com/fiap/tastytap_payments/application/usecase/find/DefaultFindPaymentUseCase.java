package br.com.fiap.tastytap_payments.application.usecase.find;

import br.com.fiap.tastytap_payments.application.PaymentGateway;

import java.util.Optional;

public class DefaultFindPaymentUseCase extends FindPaymentUseCase {

    private final PaymentGateway paymentGateway;

    public DefaultFindPaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public Optional<FindPaymentResponse> execute(Long transactionId) {
        return paymentGateway.findByTransactionId(transactionId)
                .map(FindPaymentResponse::new);
    }
}
