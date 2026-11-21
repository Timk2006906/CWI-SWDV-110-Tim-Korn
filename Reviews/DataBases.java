import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

String url = "jdbc:sqlite:products.db";
String sql = "SELECT code, description, price "
           + "FROM products ORDER BY code";

try (Connection conn = DriverManager.getConnection(url);
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(sql)) {

    while (rs.next()) {
        String code = rs.getString("code");
        String desc = rs.getString("description");
        double price = rs.getDouble("price");

        System.out.printf("%-8s %-20s %8.2f%n", code, desc, price);
    }

} catch (SQLException e) {
    System.out.println("Database error: " + e.getMessage());
}