package br.com.fiap.tastytap_payments.utils;

import java.util.concurrent.atomic.AtomicLong;

public final class NumberGenerator {

    private NumberGenerator() {}
    private static final AtomicLong number = new AtomicLong(System.currentTimeMillis()  / 50);

    public static long getNext() {
        return number.incrementAndGet();
    }
}
