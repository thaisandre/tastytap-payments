package br.com.fiap.tastytap_payments.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberGeneratorTest {

    @Test
    void getNext_shouldReturnsNextNumber() {
        Assertions.assertNotNull(NumberGenerator.getNext());
    }

}