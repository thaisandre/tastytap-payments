package br.com.fiap.tastytap_payments.infraestructure.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionTest {

    @Test
    void constructor_shouldReturnApiExceptionWithStatus500() {
        ApiException apiException = new ApiException("Custom message");
        assertThat(apiException.getMessage()).isEqualTo("Custom message");
        assertThat(apiException.getStatus()).isEqualTo(500);
    }

    @ParameterizedTest
    @CsvSource({"Custom message1,400", "Custom message2,404", "Custom message3,422"})
    void constructor_shouldReturnApiExceptionCorrectly(String message, int status) {
        ApiException apiException = new ApiException(message, status);
        assertThat(apiException.getMessage()).isEqualTo(message);
        assertThat(apiException.getStatus()).isEqualTo(status);
    }
}