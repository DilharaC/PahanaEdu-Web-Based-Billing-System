<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f0f7f0;
            margin: 0;
            padding: 30px;
        }
        form {
            max-width: 520px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2e7d32;
            text-align: center;
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
        }
        label {
            display: block;
            margin-top: 12px;
            font-weight: 600;
            color: #2e7d32;
        }
        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #c8e6c9;
            border-radius: 8px;
            background-color: #f9fff9;
            font-size: 15px;
            box-sizing: border-box;
            margin-top: 6px;
            transition: border-color 0.3s ease;
        }
        input:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #66bb6a;
            box-shadow: 0 0 6px rgba(102,187,106,0.4);
        }
        textarea {
            resize: vertical;
            min-height: 70px;
        }
        .submit-btn {
            margin-top: 20px;
            width: 100%;
            padding: 12px;
            font-weight: bold;
            background: linear-gradient(135deg, #66bb6a, #43a047);
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 6px;
            transition: background 0.3s ease;
        }
        .submit-btn:hover {
            background: linear-gradient(135deg, #57a05a, #388e3c);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 18px;
            color: #2e7d32;
            font-weight: bold;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <form action="Product?action=update" method="post">
        <h1><i class='bx bx-edit'></i> Edit Product</h1>

        <!-- Hidden field for product ID -->
        <input type="hidden" name="productId" value="${product.productId}" />

        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" value="${product.name}" required>

        <label for="description">Description:</label>
        <textarea id="description" name="description">${product.description}</textarea>

        <label for="category">Category:</label>
        <select id="category" name="category" required>
            <option value="" disabled>Select category</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat}" <c:if test="${cat == product.category}">selected</c:if>>${cat}</option>
            </c:forEach>
        </select>

        <label for="price">Price:</label>
        <input type="number" step="0.01" id="price" name="price" value="${product.price}" required>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" value="${product.quantity}" required>

        <label for="available">Available:</label>
        <select id="available" name="available">
            <option value="true" <c:if test="${product.available}">selected</c:if>>Yes</option>
            <option value="false" <c:if test="${!product.available}">selected</c:if>>No</option>
        </select>

        <button type="submit" class="submit-btn"><i class='bx bx-save'></i> Update Product</button>
    </form>

    <a href="Product" class="back-link"><i class='bx bx-arrow-back'></i> Back to Product List</a>
</body>
</html>
