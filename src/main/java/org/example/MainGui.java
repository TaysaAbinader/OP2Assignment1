package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML directly, WITHOUT a ResourceBundle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Shopping Cart Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
