package br.com.fiap.tastytap_payments.presentation.payment;

import br.com.fiap.tastytap_payments.application.usecase.update.UpdatePaymentStatusUseCase;
import br.com.fiap.tastytap_payments.presentation.payment.dtos.PaymentNotificationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentWebhookController implements PaymentWebhookControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookController.class);

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    public PaymentWebhookController(UpdatePaymentStatusUseCase updatePaymentStatusUseCase) {
        this.updatePaymentStatusUseCase = updatePaymentStatusUseCase;
    }

    @PostMapping("/payments/provider/webhook")
    public ResponseEntity<?> webhook(@Valid @RequestBody PaymentNotificationRequest request) {
        log.info("[PaymentWebhookController] Calling webhook - request: {}", request);
        updatePaymentStatusUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}
