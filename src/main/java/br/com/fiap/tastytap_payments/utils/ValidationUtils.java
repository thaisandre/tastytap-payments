package br.com.fiap.tastytap_payments.utils;

public final class ValidationUtils {

    private ValidationUtils() {}

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String string, String message) {
        notNull(string, message);
        if(string.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean predicate, String message) {
        if(!predicate) {
            throw new IllegalArgumentException(message);
        }
    }
}
