package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.pahanaedu.model.Book;

public class BookDAO {

    // Add a new book
    public void addBook(Book book) {
        String query = "INSERT INTO book (title, author, description, price, quantity, image, available) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());
            statement.setDouble(4, book.getPrice());
            statement.setInt(5, book.getQuantity());
            statement.setString(6, book.getImage());
            statement.setBoolean(7, book.isAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all books
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        Connection connection = DBConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("book_id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String desc = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            String image = resultSet.getString("image");
            boolean available = resultSet.getBoolean("available");

            books.add(new Book(id, title, author, desc, price, quantity, image, available));
        }

        return books;
    }
}