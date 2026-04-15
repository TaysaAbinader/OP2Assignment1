package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    void testCartItemFull() {
        CartItem item = new CartItem(10.0, 5);
        assertEquals(10.0, item.getPrice());
        assertEquals(5, item.getQuantity());
        assertEquals(50.0, item.getSubtotal());
    }

    @Test
    void testCalculatorFull() {
        // Test normal calculation
        assertEquals(30.0, ShoppingCartCalculator.calculate(10.0, 2, 10.0));
        // Test zero quantity (to hit any hidden branches)
        assertEquals(10.0, ShoppingCartCalculator.calculate(10.0, 0, 10.0));
    }
}
