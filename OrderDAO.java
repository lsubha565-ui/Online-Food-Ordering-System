import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDAO {

    // Customer cart-la irukura single order line-item-ai DB-la insert panna
    public boolean saveOrder(FoodOrder order) {
        String query = "INSERT INTO orders (food_id, quantity, total_price) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Mapping parameters to '?' placeholders
            stmt.setInt(1, order.getFood().getFoodId());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.calculateTotal());

            int rowsAffected = stmt.executeUpdate(); // Runs the INSERT query
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error saving order to database!");
            e.printStackTrace();
            return false;
        }
    }

    // Cart-la irukura ella orders-aiyum ore batch-ah save panna
    public boolean saveAllOrders(List<FoodOrder> cart) {
        boolean allSuccess = true;
        for (FoodOrder order : cart) {
            boolean success = saveOrder(order);
            if (!success) {
                allSuccess = false;
            }
        }
        return allSuccess;
    }
}