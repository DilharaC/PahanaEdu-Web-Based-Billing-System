package com.pahanaedu.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import com.pahanaedu.model.Book;
import com.pahanaedu.service.BookService;
/**
 * Servlet implementation class BookController
 */
@WebServlet("/Book")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1MB
maxFileSize = 1024 * 1024 * 10,      // 10MB
maxRequestSize = 1024 * 1024 * 15)   // 15MB

public class BookController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = BookService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.equals("list")) {
            listBooks(request, response);
        } else if (action.equals("add")) {
            showAddForm(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addBook(request, response);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookService.getAllBooks();
            request.setAttribute("books", bookList);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("WEB-INF/view/listBooks.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/addBook.jsp").forward(request, response);
    }

   

    protected void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean available = Boolean.parseBoolean(request.getParameter("available"));

            // Handle uploaded file
            Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
            String fileName = Path.of(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath); // Save the file to the server

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setPrice(price);
            book.setQuantity(quantity);
            book.setImage("uploads/" + fileName);  // Save relative path or just file name
            book.setAvailable(available);

            bookService.addBook(book);
            response.sendRedirect("Book?action=list");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format for price or quantity.");
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}
    
