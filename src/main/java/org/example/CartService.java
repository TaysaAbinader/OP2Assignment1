package org.example;

import java.sql.*;
import java.util.List;

public class CartService {
    public void saveCart(int totalItems, double totalCost, String langCode, List<CartItem> items) {
        String insertRecord = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
        String insertItem = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // 1. Save main record
            PreparedStatement psRecord = conn.prepareStatement(insertRecord, Statement.RETURN_GENERATED_KEYS);
            psRecord.setInt(1, totalItems);
            psRecord.setDouble(2, totalCost);
            psRecord.setString(3, langCode);
            psRecord.executeUpdate();

            // 2. Get the generated ID to link the items
            ResultSet rs = psRecord.getGeneratedKeys();
            if (rs.next()) {
                int recordId = rs.getInt(1);

                PreparedStatement psItem = conn.prepareStatement(insertItem);
                for (int i = 0; i < items.size(); i++) {
                    CartItem item = items.get(i);
                    psItem.setInt(1, recordId);
                    psItem.setInt(2, i + 1); // Item number (1, 2, 3...)
                    psItem.setDouble(3, item.getPrice());
                    psItem.setInt(4, item.getQuantity());
                    psItem.setDouble(5, item.getSubtotal());
                    psItem.executeUpdate();
                }
            }
            conn.commit(); // Save everything
            System.out.println("Cart successfully saved to Database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
