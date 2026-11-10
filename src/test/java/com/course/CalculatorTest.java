package com.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("2 + 3 = 5")
    void add() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("10 - 4 = 6")
    void subtract() {
        assertEquals(6, calculator.subtract(10, 4));
    }

    @Test
    @DisplayName("3 * 4 = 12")
    void multiply() {
        assertEquals(12, calculator.multiply(3, 4));
    }

    @Test
    @DisplayName("10 / 2 = 5")
    void divide() {
        assertEquals(5, calculator.divide(10, 2));
    }

    @Test
    @DisplayName("Divide by zero throws exception")
    void divideByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(1, 0));
    }
}
