package br.com.fiap.tastytap_payments.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static br.com.fiap.tastytap_payments.domain.Status.PENDING;
import static java.math.BigDecimal.TEN;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    @ParameterizedTest
    @NullAndEmptySource
    void constructor__shouldThrowException_whenIdIsNull(String id) {
        assertThatThrownBy(() -> new Payment(id, now(), PENDING, 1L,
                "content", 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id cannot be null or blank");
    }

    @Test
    void constructor__shouldThrowException_whenCreatedAtIsNull() {
        assertThatThrownBy(() -> new Payment("id", null, PENDING, 1L,
                "content", 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("createdAt cannot be null");
    }

    @Test
    void constructor__shouldThrowException_whenStatusIsNull() {
        assertThatThrownBy(() -> new Payment("id", now(), null,1L,
                "content", 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("status cannot be null");
    }

    @Test
    void constructor__shouldThrowException_whenTransactionIdIsNull() {
        assertThatThrownBy(() -> new Payment("id", now(), PENDING,null,
                "content", 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionId cannot be null");

        assertThatThrownBy(() -> new Payment(null,"content", 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionId cannot be null");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void constructor__shouldThrowException_whenQrCodeUrlIsNullOrBlank(String qrCodeUrl) {
        assertThatThrownBy(() -> new Payment("id", now(), PENDING,1L,
                qrCodeUrl, 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("qrCodeUrl cannot be null or blank");

        assertThatThrownBy(() -> new Payment(1L, qrCodeUrl, 1L, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("qrCodeUrl cannot be null or blank");
    }

    @Test
    void constructor__shouldThrowException_whenExternalReferenceIsNull() {
        assertThatThrownBy(() -> new Payment("id", now(), PENDING,1L,
                "content", null, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("externalReference cannot be null");

        assertThatThrownBy(() -> new Payment(1L, "content", null, TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("externalReference cannot be null");
    }

    @Test
    void constructor__shouldThrowException_whenAmountIsNull() {
        assertThatThrownBy(() -> new Payment("id", now(), PENDING,1L,
                "content", 1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount cannot be null");

        assertThatThrownBy(() -> new Payment(1L, "content", 1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount cannot be null");
    }

    @ParameterizedTest
    @ValueSource(longs = {-10L, -1L, 0})
    void constructor__shouldThrowException_whenAmountIsLessOrEqualZero(Long value) {
        assertThatThrownBy(() -> new Payment("id", now(), PENDING,1L,
                "content", 1L, BigDecimal.valueOf(value)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount must be greater than zero");

        assertThatThrownBy(() -> new Payment(1L, "content", 1L, BigDecimal.valueOf(value)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount must be greater than zero");
    }
}