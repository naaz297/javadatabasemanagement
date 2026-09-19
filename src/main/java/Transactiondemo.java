import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Transactiondemo {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/bank_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            "MyNewPass@123";

    public static void main(String[] args) {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

            System.out.println("Connected!");
            System.out.println("Database: " + conn.getCatalog());
            System.out.println(
                    "URL: " + conn.getMetaData().getURL()
            );

            conn.setAutoCommit(false);

            String sql =
                    "INSERT INTO sales_record " +
                            "(user_id, customer_name, total_amount) " +
                            "VALUES (?, ?, ?)";

            PreparedStatement stmt =
                    conn.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            stmt.setInt(1, 101);
            stmt.setString(2, "Naaz");
            stmt.setDouble(3, 1500.00);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            int salesId = 0;

            if (rs.next()) {
                salesId = rs.getInt(1);
            }

            System.out.println("Sales ID: " + salesId);

            String productSql =
                    "INSERT INTO sales_product " +
                            "(sales_id, product_name, quantity, price) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement productStmt =
                    conn.prepareStatement(productSql);

            productStmt.setInt(1, salesId);
            productStmt.setString(2, "Laptop Bag");
            productStmt.setInt(3, 2);
            productStmt.setDouble(4, 750.00);

            productStmt.executeUpdate();

            conn.commit();

            System.out.println("Transaction Committed!");

            stmt.close();
            productStmt.close();
            rs.close();

            conn.close();

            System.out.println("Connection Closed!");

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Rollback!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}
