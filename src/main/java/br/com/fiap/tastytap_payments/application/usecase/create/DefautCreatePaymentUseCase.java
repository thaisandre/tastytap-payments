package br.com.fiap.tastytap_payments.application.usecase.create;


import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.domain.Payment;

import java.util.Optional;

public class DefautCreatePaymentUseCase extends CreatePaymentUseCase{

    private final PaymentGateway paymentGateway;

    public DefautCreatePaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public Optional<CreatePaymentResponse> execute(CreatePaymentCommand command) {
        QRCodeView qrCodeView = paymentGateway.generateQRCode(command.referenceId(), command.amount());
        Payment payment = new Payment(qrCodeView.transactionId(), qrCodeView.qrCodeUrl(),
                command.referenceId(), command.amount());
        Payment persistedPayment = paymentGateway.persist(payment);
        return Optional.of(new CreatePaymentResponse(persistedPayment));
    }
}
