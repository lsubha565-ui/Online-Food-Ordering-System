public class FoodItem {
    // 1. Private fields (Encapsulation - data direct-ah access panna mudiyadhu)
    private int foodId;
    private String foodName;
    private double price;

    // 2. Parameterized Constructor
    // FoodItem item = new FoodItem(1, "Idly", 20.0); nu create pannum bodhu idhu call aagum
    public FoodItem(int foodId, String foodName, double price) {
        this.foodId = foodId;       // this.foodId refers to current class variable
        this.foodName = foodName;
        this.price = price;
    }

    // 3. Getters (Data read panna) and Setters (Data modify panna)
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}