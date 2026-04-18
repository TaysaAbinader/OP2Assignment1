package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartController {
    @FXML private ComboBox<String> langSelector;
    @FXML private Label lblPrice, lblQuantity, lblResult, lblTitle;
    @FXML private TextField txtPrice, txtQuantity;
    @FXML private Label lblTotalValue;
    @FXML private Button btnCalculate, btnConfirm;

   private LocalizationService locService = new LocalizationService();
    private CartService cartService = new CartService();

    // Track the cart session
    private double currentTotal = 0;
    private int totalItemsCount = 0;
    private List<CartItem> currentCartItems = new ArrayList<>();
    private String currentLangCode = "en";

    @FXML
    public void initialize() {
        langSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        updateLanguage("en"); // Default
    }

    @FXML
    private void handleLanguageChange() {
        String selection = langSelector.getValue();
        if (selection == null) return;

        currentLangCode = switch (selection) {
            case "Finnish" -> "fi";
            case "Swedish" -> "sv";
            case "Japanese" -> "ja";
            case "Arabic" -> "ar";
            default -> "en";
        };
        updateLanguage(currentLangCode);
    }

    private void updateLanguage(String langCode) {
        // Fetch from Database instead of properties file!
        Map<String, String> strings = locService.getLanguageStrings(langCode);

        if (!strings.isEmpty()) {
            lblPrice.setText(strings.get("prompt2"));
            lblQuantity.setText(strings.get("prompt3"));
            lblResult.setText(strings.get("result"));
            btnCalculate.setText(strings.get("calculate_button"));
        }

        // Handle Arabic RTL Layout
        if (lblTitle.getScene() != null) {
            lblTitle.getScene().getRoot().setNodeOrientation(
                langCode.equals("ar") ? javafx.geometry.NodeOrientation.RIGHT_TO_LEFT : javafx.geometry.NodeOrientation.LEFT_TO_RIGHT
            );
        }
    }

    @FXML
    private void handleCalculate() {
        try {
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQuantity.getText());

            // 1. Add item to our session list
            CartItem item = new CartItem(price, qty);
            currentCartItems.add(item);

            // 2. Update overall totals
            totalItemsCount += qty;
            currentTotal = ShoppingCartCalculator.calculate(currentTotal, qty, price);
            lblTotalValue.setText(String.format("%.2f", currentTotal));

            // 3. Save to Database!
            cartService.saveCart(totalItemsCount, currentTotal, currentLangCode, currentCartItems);

            // 4. Clear inputs for next item
            txtPrice.clear();
            txtQuantity.clear();

        } catch (NumberFormatException e) {
            System.err.println("Please enter valid numbers.");
        }
    }
}
