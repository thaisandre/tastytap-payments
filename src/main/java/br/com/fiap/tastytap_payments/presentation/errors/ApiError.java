package br.com.fiap.tastytap_payments.presentation.errors;

import br.com.fiap.tastytap_payments.infraestructure.exceptions.ApiException;

public record ApiError(String error) {

    public ApiError(ApiException apiException) {
        this(apiException.getMessage());
    }
}
