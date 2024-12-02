package br.com.fiap.tastytap_payments.presentation.payment;

import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentUseCase;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentUseCase;
import br.com.fiap.tastytap_payments.presentation.payment.dtos.CreatePaymentRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class PaymentController implements PaymentControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final CreatePaymentUseCase createPaymentUseCase;
    private final FindPaymentUseCase findPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase, FindPaymentUseCase findPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.findPaymentUseCase = findPaymentUseCase;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<FindPaymentResponse> find(@PathVariable Long transactionId) {
        log.info("[PaymentController] Calling find by transactionId: {}", transactionId);
        return findPaymentUseCase.execute(transactionId)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CreatePaymentResponse> create(@Valid @RequestBody CreatePaymentRequest command) {
        log.info("[PaymentController] Calling create payment with reference: {}", command.referenceId());
        return createPaymentUseCase.execute(command)
                .map(payment -> status(HttpStatus.CREATED).body(payment))
                .orElse(badRequest().build());
    }
}
