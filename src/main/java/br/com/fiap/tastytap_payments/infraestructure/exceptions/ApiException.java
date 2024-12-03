package br.com.fiap.tastytap_payments.infraestructure.exceptions;

public class ApiException extends RuntimeException {

    private final int status;

    public ApiException(String message) {
        super(message);
        this.status = 500;
    }

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
