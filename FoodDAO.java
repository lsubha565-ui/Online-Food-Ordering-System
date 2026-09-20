import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    // Database-la irundhu ella food items-aiyum fetch panni List<FoodItem> ah return pannum
    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodList = new ArrayList<>();
        String query = "SELECT food_id, food_name, price FROM food_items";

        // try-with-resources: Auto closes Connection and Statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // ResultSet-la irukkura ovvoru row-aiyum read panni FoodItem object-ah create panrom
            while (rs.next()) {
                int id = rs.getInt("food_id");
                String name = rs.getString("food_name");
                double price = rs.getDouble("price");

                FoodItem item = new FoodItem(id, name, price);
                foodList.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching food items from database!");
            e.printStackTrace();
        }

        return foodList;
    }

    // Single item ID vechu thedi eduka
    public FoodItem getFoodById(int foodId) {
        String query = "SELECT food_id, food_name, price FROM food_items WHERE food_id = ?";
        FoodItem item = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, foodId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("food_id");
                    String name = rs.getString("food_name");
                    double price = rs.getDouble("price");
                    item = new FoodItem(id, name, price);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching food item by ID!");
            e.printStackTrace();
        }

        return item;
    }
}