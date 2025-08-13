package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.AuditLog;
import com.pahanaedu.model.Product;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.ProductService;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/Product")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listProducts(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
   
   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addProduct(request, response);
            } else if ("update".equals(action)) {
                updateProduct(request, response);
            } else if ("delete".equals(action)) {
                deleteProduct(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        String categoryFilter = request.getParameter("category");
        List<Product> productList;

        if ((search != null && !search.trim().isEmpty()) && (categoryFilter != null && !categoryFilter.isEmpty())) {
            // Filter by both search keyword and category
            productList = productService.searchProductsByNameOrCategoryAndCategory(search.trim(), categoryFilter);
        } else if (search != null && !search.trim().isEmpty()) {
            // Filter by search keyword only (name or category)
            productList = productService.searchProductsByNameOrCategory(search.trim());
        } else if (categoryFilter != null && !categoryFilter.isEmpty()) {
            // Filter by category only
            productList = productService.getProductsByCategory(categoryFilter);
        } else {
            // No filters, get all products
            productList = productService.getAllProducts();
        }

        List<String> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);
        request.setAttribute("products", productList);
        request.getRequestDispatcher("WEB-INF/view/listProducts.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can get categories from DB in real app
        List<String> categories = List.of("Books", "Stationery", "Office Supplies", "Art & Craft", "Gifts", "School Accessories");
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("WEB-INF/view/addProduct.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {

        List<String> categories = List.of(
            "Books", 
            "Stationery", 
            "Office Supplies", 
            "Art & Craft", 
            "Gifts", 
            "School Accessories"
        );
        request.setAttribute("categories", categories);

        int productId = Integer.parseInt(request.getParameter("productId"));
        Product existingProduct = productService.getProductById(productId);
        request.setAttribute("product", existingProduct);

        request.getRequestDispatcher("WEB-INF/view/editProduct.jsp").forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Extract product info
        Product product = extractProductFromRequest(request);

        // Add product and get generated ID
        int productId = ProductService.getInstance().addProductAndGetId(product);
        product.setProductId(productId); // now you have the correct ID

        // Audit log
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        AuditLog log = new AuditLog();
        log.setAction("Add Product");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Product");
        log.setTargetId(product.getProductId());
        log.setDetails("Added product: " + product.getName() + " (ID: " + product.getProductId() + ")");
        AuditLogService.getInstance().logAction(log);

        response.sendRedirect("Product?action=list");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        // Get existing product from DB
        Product oldProduct = productService.getProductById(productId);
        
        // Get new values from request
        Product newProduct = extractProductFromRequest(request);
        newProduct.setProductId(productId);
        
        // Update product in DB
        productService.updateProduct(newProduct);

        // Build details string for audit log
        StringBuilder changes = new StringBuilder();
        if (!oldProduct.getName().equals(newProduct.getName())) {
            changes.append("Name: '").append(oldProduct.getName()).append("' → '").append(newProduct.getName()).append("'; ");
        }
        if (!oldProduct.getDescription().equals(newProduct.getDescription())) {
            changes.append("Description: '").append(oldProduct.getDescription()).append("' → '").append(newProduct.getDescription()).append("'; ");
        }
        if (!oldProduct.getCategory().equals(newProduct.getCategory())) {
            changes.append("Category: '").append(oldProduct.getCategory()).append("' → '").append(newProduct.getCategory()).append("'; ");
        }
        if (oldProduct.getPrice() != newProduct.getPrice()) {
            changes.append("Price: ").append(oldProduct.getPrice()).append(" → ").append(newProduct.getPrice()).append("; ");
        }
        if (oldProduct.getQuantity() != newProduct.getQuantity()) {
            changes.append("Quantity: ").append(oldProduct.getQuantity()).append(" → ").append(newProduct.getQuantity()).append("; ");
        }
        if (oldProduct.isAvailable() != newProduct.isAvailable()) {
            changes.append("Available: ").append(oldProduct.isAvailable()).append(" → ").append(newProduct.isAvailable()).append("; ");
        }

        // Audit log
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        AuditLog log = new AuditLog();
        log.setAction("Update Product");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Product");
        log.setTargetId(productId);
        log.setDetails(changes.toString().isEmpty() ? "No changes made" : changes.toString());
        AuditLogService.getInstance().logAction(log);

        response.sendRedirect("Product?action=list");
    }
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        // Get the product details before deleting
        Product product = productService.getProductById(productId);

        // Delete product from DB
        productService.deleteProduct(productId);

        // Audit log
        HttpSession session = request.getSession(false);
        String performedBy = "Unknown";
        if (session != null && session.getAttribute("staff") != null) {
            performedBy = ((Staff) session.getAttribute("staff")).getFullName();
        }

        AuditLog log = new AuditLog();
        log.setAction("Delete Product");
        log.setPerformedBy(performedBy);
        log.setTargetEntity("Product");
        log.setTargetId(productId);
        log.setDetails("Deleted product: " + product.getName() + " (ID: " + productId + ", Category: " + product.getCategory() + ")");
        AuditLogService.getInstance().logAction(log);

        response.sendRedirect("Product?action=list");
    }
    // Helper method to parse product data from request parameters
    private Product extractProductFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        boolean available = Boolean.parseBoolean(request.getParameter("available"));

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setAvailable(available);

        return product;
    }
    
}