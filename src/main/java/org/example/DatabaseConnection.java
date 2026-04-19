package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Read the DB_HOST environment variable. If it's null, default to "localhost"
    private static final String HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";

    // Dynamically build the URL
    private static final String URL = "jdbc:mariadb://" + HOST + ":3306/shopping_cart_localization";

    private static final String USER = "root";
    private static final String PASS = "YOUR_DATABASE_PASSWORD_HERE";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
