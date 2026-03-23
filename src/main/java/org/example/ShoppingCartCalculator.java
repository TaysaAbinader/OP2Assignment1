package org.example;

public class ShoppingCartCalculator {

    public static double calculate (double currentTotal, double quantity, double price) {
        return currentTotal + (quantity * price);
    }
}
