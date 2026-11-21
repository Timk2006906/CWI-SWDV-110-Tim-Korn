
package edu.cwi.swdv110.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookDAO {
    private final String url;

    public BookDAO(String url) {
        this.url = url;
    }

    private Book toBook(ResultSet rs) throws SQLException {
        return new Book(
            rs.getInt("id"),
            rs.getString("isbn"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getInt("year"),
            rs.getInt("rating"),
            rs.getInt("is_read") == 1
        );
    }

    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(toBook(rs));
            }
        }

        return books;
    }

    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM books WHERE isbn = ?";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(toBook(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<Book> search(String term) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? ORDER BY title";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + term + "%");
            ps.setString(2, "%" + term + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(toBook(rs));
                }
            }
        }

        return books;
    }

    public List<Book> unread() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE is_read = 0 ORDER BY title";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(toBook(rs));
            }
        }

        return books;
    }

    public int add(Book b) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, year, rating, is_read) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.isbn());
            ps.setString(2, b.title());
            ps.setString(3, b.author());
            ps.setInt(4, b.year());
            ps.setInt(5, b.rating());
            ps.setInt(6, b.read() ? 1 : 0);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("No generated ID returned");
        }
    }

    public boolean update(Book b) throws SQLException {
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, "
                   + "year = ?, rating = ?, is_read = ? WHERE id = ?";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.isbn());
            ps.setString(2, b.title());
            ps.setString(3, b.author());
            ps.setInt(4, b.year());
            ps.setInt(5, b.rating());
            ps.setInt(6, b.read() ? 1 : 0);
            ps.setInt(7, b.id());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean markRead(String isbn, int rating) throws SQLException {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(
                "Rating must be between 1 and 5"
            );
        }

        String sql = "UPDATE books SET is_read = 1, rating = ? WHERE isbn = ?";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating);
            ps.setString(2, isbn);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);
            return ps.executeUpdate() > 0;
        }
    }

    public Map<String, Integer> countByAuthor() throws SQLException {
        Map<String, Integer> counts = new LinkedHashMap<>();

        String sql = "SELECT author, COUNT(*) AS total FROM books "
                   + "GROUP BY author ORDER BY total DESC, author ASC";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                counts.put(
                    rs.getString("author"),
                    rs.getInt("total")
                );
            }
        }

        return counts;
    }

    public double averageRating() throws SQLException {
        String sql = "SELECT AVG(rating) AS average "
                   + "FROM books WHERE rating > 0";

        try (Connection conn = Database.connect(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("average");
            }
        }

        return 0.0;
    }
}
