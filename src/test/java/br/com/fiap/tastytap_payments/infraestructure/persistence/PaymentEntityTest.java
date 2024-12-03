package br.com.fiap.tastytap_payments.infraestructure.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentEntityTest {

    @Test
    void constructor__shouldThrowException_whenPaymentIsNull() {
        assertThatThrownBy(() -> new PaymentEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("payment cannot be null");
    }

}