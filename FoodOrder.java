class FoodOrder {

    private int orderId;
    private FoodItem food;
    private int quantity;

    FoodOrder(int orderId, FoodItem food, int quantity) {
        this.orderId = orderId;
        this.food = food;
        this.quantity = quantity;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setFood(FoodItem food) {
        this.food = food;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public FoodItem getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateTotal() {
        return food.getPrice() * quantity;
    }
}

