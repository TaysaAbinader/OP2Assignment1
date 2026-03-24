package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartCalculatorTest {

    @Test
    void testSingleItemCalculation() {
        double currentTotal = 0.0;
        double quantity = 2.0;
        double price = 10.0;

        double result = ShoppingCartCalculator.calculate(currentTotal, quantity, price);

        // Expected: 0 + (2 * 10) = 20.0
        assertEquals(20.0, result, "The calculation for a single item is incorrect.");
    }

    @Test
    void testAccumulation() {
        double total = 0.0;

        // Add first item: 2 x 5.0 = 10.0
        total = ShoppingCartCalculator.calculate(total, 2.0, 5.0);

        // Add second item: 1 x 15.5 = 15.5
        total = ShoppingCartCalculator.calculate(total, 1.0, 15.5);

        // Expected: 10.0 + 15.5 = 25.5
        assertEquals(25.5, total, 0.001, "The total did not accumulate correctly.");
    }

    @Test
    void testZeroQuantity() {
        double result = ShoppingCartCalculator.calculate(50.0, 0.0, 10.0);
        assertEquals(50.0, result, "Total should not change if quantity is zero.");
    }
}