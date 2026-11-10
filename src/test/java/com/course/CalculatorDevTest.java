package com.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorDevTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    void multiplyByZero() {
        assertEquals(0, calculator.multiply(7, 0));
    }
}
