<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
</head>
<body>
    <h1>Add Book</h1>
   <form action="Book?action=add" method="post" enctype="multipart/form-data">
        
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br><br>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required><br><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description"></textarea><br><br>

        <label for="price">Price:</label>
        <input type="number" step="0.01" id="price" name="price" required><br><br>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" required><br><br>

        <label for="image">Upload Image:</label>
<input type="file" id="image" name="image"><br><br>

        <label for="available">Available:</label>
        <select id="available" name="available">
            <option value="true" selected>Yes</option>
            <option value="false">No</option>
        </select><br><br>

        <input type="submit" value="Add Book">
    </form>
</body>
</html>