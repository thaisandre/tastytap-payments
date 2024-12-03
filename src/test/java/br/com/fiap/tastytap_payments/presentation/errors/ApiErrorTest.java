package br.com.fiap.tastytap_payments.presentation.errors;

import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ApiErrorTest {

    @Test
    void constructor_shouldReturnApiErrorWithStatus500() {
        ApiError apiError = new ApiError(new ApiException("Custom message"));
        assertThat(apiError.error()).isEqualTo("Custom message");
        assertThat(apiError.status()).isEqualTo(500);
    }

    @ParameterizedTest
    @CsvSource({"Custom message1,400", "Custom message2,404", "Custom message3,422"})
    void constructor_shouldReturnApiErrorCorrectly(String message, int status) {
        ApiError apiError = new ApiError(new ApiException(message, status));
        assertThat(apiError.error()).isEqualTo(message);
        assertThat(apiError.status()).isEqualTo(status);
    }

}