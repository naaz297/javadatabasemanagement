import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transactiondemo {

    private static final String URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "MyNewPass@123";

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connected to the Database!");

            // Transaction start
            conn.setAutoCommit(false);

            // 1. Insert Order
            String orderSql =
                    "INSERT INTO my_orders (user_id, customer_name, total_amount) VALUES (?, ?, ?)";

            PreparedStatement orderStmt =
                    conn.prepareStatement(orderSql);

            orderStmt.setInt(1, 101);
            orderStmt.setString(2, "Naaz");
            orderStmt.setDouble(3, 1500.00);

            orderStmt.executeUpdate();

            // 2. Get Order ID
            int orderId = 1;

            // 3. Insert Order Item
            String itemSql =
                    "INSERT INTO my_order_items " +
                            "(order_id, product_name, quantity, price) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement itemStmt =
                    conn.prepareStatement(itemSql);

            itemStmt.setInt(1, orderId);
            itemStmt.setString(2, "Laptop Bag");
            itemStmt.setInt(3, 2);
            itemStmt.setDouble(4, 750.00);

            itemStmt.executeUpdate();

            // Everything successful
            conn.commit();

            System.out.println("Order inserted successfully!");
            System.out.println("Order Item inserted successfully!");

        } catch (SQLException e) {

            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Transaction Rollback!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}