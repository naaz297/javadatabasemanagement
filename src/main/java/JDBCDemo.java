
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {

    private static final String URL =
            "jdbc:mysql://localhost:3306/bank_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            "MyNewPass@123";

    public static void main(String[] args) {

        try {

            // 1. Connect to database
            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

            // ================= INSERT =================

            String insertQuery =
                    "INSERT INTO studentts (id, name, age, course) VALUES (?, ?, ?, ?)";

            PreparedStatement insert =
                    conn.prepareStatement(insertQuery);

            insert.setInt(1, 2);
            insert.setString(2, "Nazrin");
            insert.setInt(3, 22);
            insert.setString(4, "CSE");


            insert.executeUpdate();

            System.out.println("Student inserted successfully!");


            // ================= SELECT =================

            String selectQuery =
                    "SELECT * FROM studentts";

            PreparedStatement select =
                    conn.prepareStatement(selectQuery);

            ResultSet rs =
                    select.executeQuery();

            System.out.println("\nStudent Records:");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getInt("age") + " " +
                                rs.getString("course")
                );
            }


            // ================= UPDATE =================

            String updateQuery =
                    "UPDATE studentts SET age = ? WHERE id = ?";

            PreparedStatement update =
                    conn.prepareStatement(updateQuery);

            update.setInt(1, 23);
            update.setInt(2, 2);

            update.executeUpdate();

            System.out.println("\nStudent updated successfully!");


            // ================= DELETE =================

            String deleteQuery =
                    "DELETE FROM studentts WHERE id = ?";

            PreparedStatement delete =
                    conn.prepareStatement(deleteQuery);

            delete.setInt(1, 2);

            delete.executeUpdate();

            System.out.println("Student deleted successfully!");


            // ================= CLOSE =================

            rs.close();
            insert.close();
            select.close();
            update.close();
            delete.close();
            conn.close();

            System.out.println("\nConnection closed.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}

