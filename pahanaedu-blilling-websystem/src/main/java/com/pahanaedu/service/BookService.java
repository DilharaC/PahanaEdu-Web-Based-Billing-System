package com.pahanaedu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.BookDAO;
import com.pahanaedu.model.Book;
public class BookService {

    private static BookService instance;
    private BookDAO bookDAO;

    private BookService() {
        this.bookDAO = new BookDAO();
    }

    public static BookService getInstance() {
        if (instance == null) {
            synchronized (BookService.class) {
                if (instance == null) {
                    instance = new BookService();
                }
            }
        }
        return instance;
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAllBooks();
    }
}