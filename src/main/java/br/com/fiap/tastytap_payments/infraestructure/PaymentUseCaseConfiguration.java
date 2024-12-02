package br.com.fiap.tastytap_payments.infraestructure;

import br.com.fiap.tastytap_payments.application.PaymentGateway;
import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.create.DefautCreatePaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.find.DefaultFindPaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.update.DefaultUpdatePaymentStatusUseCase;
import br.com.fiap.tastytap_payments.application.usecase.update.UpdatePaymentStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCaseConfiguration {

    private final PaymentGateway paymentGateway;

    public PaymentUseCaseConfiguration(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Bean
    public UpdatePaymentStatusUseCase updateStatusUseCase() {
        return new DefaultUpdatePaymentStatusUseCase(paymentGateway);
    }

    @Bean
    public CreatePaymentUseCase createPaymentUseCase() {
        return new DefautCreatePaymentUseCase(paymentGateway);
    }

    @Bean
    public FindPaymentUseCase findPaymentUseCase() {
        return new DefaultFindPaymentUseCase(paymentGateway);
    }
}
