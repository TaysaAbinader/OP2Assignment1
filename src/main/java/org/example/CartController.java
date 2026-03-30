package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class CartController {
    @FXML private ComboBox<String> langSelector;
    @FXML private Label lblPrice, lblQuantity, lblResult, lblTitle;
    @FXML private TextField txtPrice, txtQuantity;
    @FXML private Label lblTotalValue;
    @FXML private Button btnCalculate, btnConfirm;

    private ResourceBundle bundle;
    private double currentTotal = 0;

    @FXML
    public void initialize() {
        langSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        // Default to English
        updateLanguage(new Locale("en", "US"));
    }

    @FXML
    private void handleLanguageChange() {
        String selection = langSelector.getValue();
        if (selection == null) return;

        Locale locale = switch (selection) {
            case "Finnish" -> new Locale("fi", "FI");
            case "Swedish" -> new Locale("sv", "SE");
            case "Japanese" -> new Locale("ja", "JP");
            case "Arabic" -> new Locale("ar", "AR");
            default -> new Locale("en", "US");
        };
        updateLanguage(locale);
    }

    private void updateLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("MessagesBunble", locale);

        // Update UI text from properties file
        lblPrice.setText(bundle.getString("prompt2")); // "Enter price"
        lblQuantity.setText(bundle.getString("prompt3")); // "Enter quantity"
        lblResult.setText(bundle.getString("result")); // "Total cost"
        btnCalculate.setText(bundle.getString("calculate_button")); // Add this key to your .properties

        // Handle Arabic RTL
        if (lblTitle.getScene() != null) {
            lblTitle.getScene().getRoot().setNodeOrientation(
                locale.getLanguage().equals("ar") ?
                javafx.geometry.NodeOrientation.RIGHT_TO_LEFT :
                javafx.geometry.NodeOrientation.LEFT_TO_RIGHT
            );
        }
    }

    @FXML
    private void handleCalculate() {
        try {
            double price = Double.parseDouble(txtPrice.getText());
            double qty = Double.parseDouble(txtQuantity.getText());

            // Reusing your static backend method!
            currentTotal = ShoppingCartCalculator.calculate(currentTotal, qty, price);

            lblTotalValue.setText(String.format("%.2f", currentTotal));

            // Clear inputs for next item
            txtPrice.clear();
            txtQuantity.clear();
        } catch (NumberFormatException e) {
            // Show error if user enters text instead of numbers
        }
    }
}
