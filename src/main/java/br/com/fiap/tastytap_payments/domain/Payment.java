package br.com.fiap.tastytap_payments.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static br.com.fiap.tastytap_payments.utils.ValidationUtils.*;

public class Payment {

    private String id = UUID.randomUUID().toString();
    private LocalDateTime createdAt = LocalDateTime.now();
    private Status status = PENDING;
    private final Long transactionId;
    private final String qrCodeUrl;
    private final Long externalReference;
    private final BigDecimal amount;

    public Payment(String id, LocalDateTime createdAt, Status status, Long transactionId,
                   String qrCodeUrl, Long externalReference, BigDecimal amount) {
        notBlank(id, "id cannot be null or blank");
        notNull(createdAt, "createdAt cannot be null");
        notNull(status, "status cannot be null");
        notNull(transactionId, "transactionId cannot be null");
        notBlank(qrCodeUrl, "qrCodeUrl cannot be null or blank");
        notNull(externalReference, "externalReference cannot be null");
        notNull(amount, "amount cannot be null");
        isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "amount must be greater than zero");
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.transactionId = transactionId;
        this.qrCodeUrl = qrCodeUrl;
        this.externalReference = externalReference;
        this.amount = amount;
    }

    public Payment(Long transactionId, String qrCodeUrl, Long externalReference, BigDecimal amount) {
        notNull(transactionId, "transactionId cannot be null");
        notBlank(qrCodeUrl, "qrCodeUrl cannot be null or blank");
        notNull(externalReference, "externalReference cannot be null");
        notNull(amount, "amount cannot be null");
        isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "amount must be greater than zero");
        this.transactionId = transactionId;
        this.qrCodeUrl = qrCodeUrl;
        this.externalReference = externalReference;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public Long getExternalReference() {
        return externalReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
