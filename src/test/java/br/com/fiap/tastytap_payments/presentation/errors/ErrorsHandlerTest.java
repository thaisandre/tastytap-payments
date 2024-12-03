package br.com.fiap.tastytap_payments.presentation.errors;

import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorsHandlerTest {

    private ErrorsHandler errorsHandler;

    @BeforeEach
    void setUp() {
        errorsHandler = new ErrorsHandler();
    }

    @Test
    void handler_shouldReturnEmptyValidationErrors_whenExceptionHasNoBindingResultErrors() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ValidationError validationError = errorsHandler.handler(exception);

        assertThat(validationError.getFieldErrors()).isEmpty();
    }

    @Test
    void handler_shouldReturnValidationErrors_whenExceptionHasNoBindingResultErrors() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("object", "field", "default message")));
        when(bindingResult.getGlobalErrors()).thenReturn(List.of(new ObjectError("object", "default message")));

        ValidationError validationError = errorsHandler.handler(exception);

        assertThat(validationError.getFieldErrors()).isNotEmpty();
        assertThat(validationError.getGlobalErrors()).isNotEmpty();
    }

    @Test
    void handlerApiException_shouldReturnApiErrorCorrectly() {
        ApiException exception = new ApiException("custom message", 400);

        ResponseEntity<ApiError> apiErrorResponse = errorsHandler.handlerApiException(exception);

        assertThat(apiErrorResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(apiErrorResponse.getBody().status()).isEqualTo(400);
        assertThat(apiErrorResponse.getBody().error()).isEqualTo("custom message");
    }
}