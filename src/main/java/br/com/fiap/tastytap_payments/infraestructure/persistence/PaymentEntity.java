package br.com.fiap.tastytap_payments.infraestructure.persistence;

import br.com.fiap.tastytap_payments.domain.Payment;
import br.com.fiap.tastytap_payments.domain.Status;
import br.com.fiap.tastytap_payments.utils.ValidationUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
public class PaymentEntity {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private Status status;
    private Long transactionId;
    private String qrCodeUrl;
    private Long externalReference;
    private BigDecimal amount;

    @Deprecated
    public PaymentEntity() {}

    public PaymentEntity(Payment payment) {
        ValidationUtils.notNull(payment, "payment cannot be null");
        this.id = payment.getId();
        this.createdAt = payment.getCreatedAt();
        this.status = payment.getStatus();
        this.transactionId = payment.getTransactionId();
        this.qrCodeUrl = payment.getQrCodeUrl();
        this.status = payment.getStatus();
        this.createdAt = payment.getCreatedAt();
        this.externalReference = payment.getExternalReference();
        this.amount = payment.getAmount();
    }

    public Payment toDomain() {
        return new Payment(id, createdAt, status, transactionId, qrCodeUrl, externalReference, amount);
    }
}
