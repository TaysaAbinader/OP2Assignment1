package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {
    public Map<String, String> getLanguageStrings(String langCode) {
        Map<String, String> translations = new HashMap<>();
        String query = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, langCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                translations.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return translations;
    }
}
