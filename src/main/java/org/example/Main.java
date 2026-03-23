package org.example;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Select a language: ");
        System.out.println("1. English");
        System.out.print("2. Finnish ");
        System.out.print("3. Swedish ");
        System.out.print("4. Japanese ");
        int choice = input.nextInt();
        Locale locale;
        switch (choice) {
            case 2:
                locale = new Locale("fi", "FI");
                break;
            case 3:
                locale = new Locale("sv", "SE");
                break;
            case 4:
                locale = new Locale("ja", "JP");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }
        ResourceBundle rb = ResourceBundle.getBundle("MessagesBunble", locale);

        System.out.println(rb.getString("prompt1"));
        int numberOfItens = input.nextInt();

        double totalSum = 0;

        for (int i = 0; i < numberOfItens; i++) {

            System.out.println(rb.getString("prompt3"));
            double quantityPerItem = input.nextDouble();

            System.out.println(rb.getString("prompt2"));
            double pricePerItem = input.nextDouble();

            totalSum = ShoppingCartCalculator.calculate(totalSum, quantityPerItem, pricePerItem);

        }

        System.out.println(rb.getString("result") + " " + totalSum);


    }
}