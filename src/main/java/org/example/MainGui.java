package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.util.Locale;

public class MainGui extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Set the default language (English)
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBunble", locale);

        // 2. Load the FXML layout
        // NOTE: Ensure your .fxml file is named "scene.fxml" in src/main/resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene.fxml"), bundle);
        Parent root = loader.load();

        // 3. Setup the Window
        primaryStage.setTitle("Taysa Abinader / Shopping Cart App");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }
}
