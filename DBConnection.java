import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // IMPORTANT: Replace 'your_mysql_password' with your real MySQL password!
    private static final String URL = "jdbc:mysql://localhost:3306/food_ordering?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC Driver not found! Jar file missing in classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Failed to connect to MySQL! Check password or DB name.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        System.out.println("Connecting to database...");
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("SUCCESS: Connected to MySQL food_ordering database successfully!");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("FAILED: Could not establish connection.");
        }
    }
}