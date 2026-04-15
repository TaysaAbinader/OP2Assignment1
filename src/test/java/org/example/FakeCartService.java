package org.example;
import java.util.List;

public class FakeCartService extends CartService {
    @Override
    public void saveCart(int totalItems, double totalCost, String langCode, List<CartItem> items) {
        // Do nothing. This prevents the test from trying to connect to a real database.
        System.out.println("Fake DB: Save suppressed for testing.");
    }
}
