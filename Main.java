import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodDAO foodDAO = new FoodDAO();
        OrderDAO orderDAO = new OrderDAO(); // DAO instance for handling orders
        List<FoodOrder> cart = new ArrayList<>();
        int orderCounter = 101;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=================================");
            System.out.println("   ONLINE FOOD ORDERING SYSTEM   ");
            System.out.println("=================================");
            System.out.println("1. View Food Menu");
            System.out.println("2. Place Order / Add to Cart");
            System.out.println("3. View Cart & Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayMenu(foodDAO);
                    break;

                case 2:
                    displayMenu(foodDAO);
                    System.out.print("Enter Food ID to order: ");
                    int foodId = scanner.nextInt();

                    FoodItem selectedFood = foodDAO.getFoodById(foodId);
                    if (selectedFood == null) {
                        System.out.println("Error: Food item not found with ID " + foodId);
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    if (quantity <= 0) {
                        System.out.println("Error: Quantity must be at least 1.");
                        break;
                    }

                    cart.add(new FoodOrder(orderCounter++, selectedFood, quantity));
                    System.out.println("Added " + quantity + " x " + selectedFood.getFoodName() + " to your cart!");
                    break;

                case 3:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty! Add items using option 2 first.");
                    } else {
                        printBill(cart);

                        // Save orders permanently into MySQL
                        System.out.println("Saving order to database...");
                        boolean saved = orderDAO.saveAllOrders(cart);

                        if (saved) {
                            System.out.println("Order successfully saved into MySQL database!");
                        } else {
                            System.out.println("Warning: Failed to save some or all orders to database.");
                        }

                        cart.clear(); // Empty the cart after successful checkout
                        System.out.println("Checkout complete! Thank you.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting application. Thank you for visiting!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 4.");
            }
        }

        scanner.close();
    }

    private static void displayMenu(FoodDAO foodDAO) {
        List<FoodItem> menu = foodDAO.getAllFoodItems();

        if (menu.isEmpty()) {
            System.out.println("No food items available in the menu right now.");
            return;
        }

        System.out.println("\n----------------- FOOD MENU (From MySQL) -----------------");
        System.out.printf("%-6s %-20s %-10s%n", "ID", "Item Name", "Price");
        System.out.println("----------------------------------------------------------");
        for (FoodItem item : menu) {
            System.out.printf("%-6d %-20s Rs. %-8.2f%n", item.getFoodId(), item.getFoodName(), item.getPrice());
        }
        System.out.println("----------------------------------------------------------");
    }

    private static void printBill(List<FoodOrder> cart) {
        double grandTotal = 0.0;

        System.out.println("\n=================== FINAL BILL ===================");
        System.out.printf("%-20s %-8s %-10s %-10s%n", "Item", "Qty", "Price", "Subtotal");
        System.out.println("--------------------------------------------------");

        for (FoodOrder order : cart) {
            double subtotal = order.calculateTotal();
            grandTotal += subtotal;

            System.out.printf("%-20s %-8d Rs. %-7.2f Rs. %-8.2f%n",
                    order.getFood().getFoodName(),
                    order.getQuantity(),
                    order.getFood().getPrice(),
                    subtotal);
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Grand Total: Rs. %.2f%n", grandTotal);
        System.out.println("==================================================");
    }
}