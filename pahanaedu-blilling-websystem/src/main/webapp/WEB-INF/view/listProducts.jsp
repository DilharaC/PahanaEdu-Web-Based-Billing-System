<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Product Management</title>

<!-- Boxicons CDN -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4fff4;
        margin: 0;
        padding: 0;
        transition: background-color 0.3s ease, color 0.3s ease;
    }
    .header {
        background-color: #66bb6a;
        color: white;
        padding: 15px;
        text-align: center;
        position: relative; /* needed for positioning the back home btn */
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 8px;
        transition: background-color 0.3s ease, color 0.3s ease;
    }
    .header h1 {
        font-size: 1.8rem;
        margin: 0;
        flex-grow: 1;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 8px;
    }
    .back-home-btn {
        position: absolute;
        right: 15px;
        top: 50%;
        transform: translateY(-50%);
        background-color: #4caf50;
        color: white;
        padding: 8px 15px;
        text-decoration: none;
        border-radius: 4px;
        display: inline-flex;
        align-items: center;
        gap: 6px;
        font-weight: bold;
        font-size: 0.9rem;
        transition: background-color 0.3s ease;
        white-space: nowrap;
        user-select: none;
        box-shadow: 0 2px 5px rgba(0,0,0,0.2);
    }
    .back-home-btn:hover {
        background-color: #3e8e41;
    }
    .container {
        display: flex;
        gap: 20px;
        padding: 20px;
        align-items: flex-start;
        color: inherit;
    }
    /* Fixed Sidebar */
    .sidebar {
        width: 250px;
        background: white;
        padding: 15px;
        border: 1px solid #ccc;
        border-radius: 5px;
        position: sticky;
        top: 20px;
        height: 95vh;
        box-sizing: border-box;
        transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease;
    }
    .form-control {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border-radius: 4px;
        border: 1px solid #ccc;
        box-sizing: border-box;
        transition: border-color 0.3s ease, background-color 0.3s ease, color 0.3s ease;
    }
    .sidebar h3 {
        color: #388e3c;
        display: flex;
        align-items: center;
        gap: 6px;
        margin-top: 0;
        transition: color 0.3s ease;
    }
    .search-btn {
        background-color: #66bb6a;
        color: white;
        padding: 10px;
        width: 100%;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 5px;
        font-weight: bold;
        transition: background-color 0.3s ease;
        user-select: none;
    }
    .search-btn:hover {
        background-color: #57a05a;
    }
    .content {
        flex: 1;
        background: white;
        padding: 15px;
        border: 1px solid #ccc;
        border-radius: 5px;
        color: inherit;
        transition: background-color 0.3s ease, border-color 0.3s ease;
    }
    .content-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 15px;
        align-items: center;
    }
    .add-btn {
        background-color: #4caf50;
        color: white;
        padding: 8px 15px;
        text-decoration: none;
        border-radius: 4px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        gap: 6px;
        min-width: 120px;
        user-select: none;
        transition: background-color 0.3s ease;
    }
    .add-btn:hover {
        background-color: #3e8e41;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        transition: color 0.3s ease;
    }
    th {
        background-color: #a5d6a7;
        padding: 10px;
        text-align: left;
        transition: background-color 0.3s ease, color 0.3s ease;
    }
    td {
        padding: 8px;
        border-bottom: 1px solid #ddd;
        transition: color 0.3s ease;
    }
    tr:hover {
        background-color: #f1f8f1;
        transition: background-color 0.3s ease;
    }
    .btn-edit {
        background-color: #42a5f5;
        color: white;
        padding: 5px 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 4px;
        user-select: none;
        transition: background-color 0.3s ease;
    }
    .btn-edit:hover {
        background-color: #1e88e5;
    }
    .btn-delete {
        background-color: #ef5350;
        color: white;
        padding: 5px 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 4px;
        user-select: none;
        transition: background-color 0.3s ease;
    }
    .btn-delete:hover {
        background-color: #c62828;
    }

    /* Dark Mode Styles */
    body.dark-mode {
        background-color: #121212;
        color: #ddd;
    }
    body.dark-mode .header {
        background-color: #1b5e20;
        color: #c8e6c9;
    }
    body.dark-mode .back-home-btn {
        background-color: #2e7d32;
        color: #c8e6c9;
        box-shadow: 0 2px 5px rgba(0,0,0,0.7);
    }
    body.dark-mode .back-home-btn:hover {
        background-color: #1b5e20;
    }
    body.dark-mode .container {
        color: #ccc;
    }
    body.dark-mode .sidebar {
        background-color: #1e1e1e;
        border-color: #3e8e41;
        color: #a5d6a7;
    }
    body.dark-mode .form-control {
        background-color: #3a3a3a;
        border-color: #4caf50;
        color: #ddd;
    }
    body.dark-mode .form-control:focus {
        border-color: #81c784;
        outline: none;
    }
    body.dark-mode .sidebar h3 {
        color: #81c784;
    }
    body.dark-mode .search-btn {
        background-color: #4caf50;
        color: #e8f5e9;
    }
    body.dark-mode .search-btn:hover {
        background-color: #388e3c;
    }
    body.dark-mode .content {
        background-color: #1e1e1e;
        border-color: #3e8e41;
        color: #ccc;
    }
    body.dark-mode table, 
    body.dark-mode th, 
    body.dark-mode td {
        border-color: #4caf50;
    }
    body.dark-mode th {
        background-color: #388e3c;
        color: #e8f5e9;
    }
    body.dark-mode tr:hover {
        background-color: #3e8e41;
    }
    body.dark-mode .btn-edit {
        background-color: #2196f3;
        color: #e3f2fd;
    }
    body.dark-mode .btn-edit:hover {
        background-color: #1565c0;
    }
    body.dark-mode .btn-delete {
        background-color: #e53935;
        color: #ffcdd2;
    }
    body.dark-mode .btn-delete:hover {
        background-color: #b71c1c;
    }
</style>
</head>
<body>

<div class="header">
    <h1><i class='bx bxs-cube-alt'></i> Product Management</h1>
    <c:choose>
      <c:when test="${sessionScope.role == 'admin'}">
        <a href="${pageContext.request.contextPath}/AdminDashboard?action=dashboard" class="back-home-btn">
          <i class='bx bx-home'></i> Home
        </a>
      </c:when>
      <c:otherwise>
        <a href="${pageContext.request.contextPath}/StaffDashboard?action=dashboard" class="back-home-btn">
          <i class='bx bx-home'></i> Home
        </a>
      </c:otherwise>
    </c:choose>
</div>

<div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
        <h3><i class='bx bx-filter'></i> Filters</h3>
        <form method="get" action="Product">
            <input type="text" name="search" value="${param.search}" placeholder="Search..." class="form-control">
            <select name="category" class="form-control">
                <option value="">All Categories</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat}" <c:if test="${cat == param.category}">selected</c:if>>${cat}</option>
                </c:forEach>
            </select>
            <button type="submit" class="search-btn"><i class='bx bx-search'></i> Search</button>
        </form>
    </div>

    <!-- Content -->
    <div class="content">
        <div class="content-header">
            <h2><i class='bx bx-list-ul'></i> Product List</h2>
            <a href="Product?action=add" class="add-btn"><i class='bx bx-plus'></i> Add Product</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th><i class='bx bx-hash'></i> ID</th>
                    <th><i class='bx bx-box'></i> Name</th>
                    <th><i class='bx bx-category'></i> Category</th>
                    <th><i class='bx bx-detail'></i> Description</th>
                    <th><i class='bx bx-dollar'></i> Price</th>
                    <th><i class='bx bx-package'></i> Stock</th>
                    <th><i class='bx bx-check-circle'></i> Status</th>
                    <th><i class='bx bx-cog'></i> Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.name}</td>
                        <td>${product.category}</td>
                        <td>${product.description}</td>
                        <td>$${product.price}</td>
                        <td>${product.quantity}</td>
                        <td>
                            <c:choose>
                                <c:when test="${product.available}">
                                    <i class='bx bx-check-circle' style="color:green;"></i> Available
                                </c:when>
                                <c:otherwise>
                                    <i class='bx bx-x-circle' style="color:red;"></i> Unavailable
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="Product" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <button type="submit" class="btn-edit"><i class='bx bx-edit'></i> Edit</button>
                            </form>
                            <form action="Product" method="post" style="display:inline;" onsubmit="return confirm('Delete this product?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <button type="submit" class="btn-delete"><i class='bx bx-trash'></i> Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    // Automatically apply dark mode if localStorage.theme is 'dark'
    if(localStorage.getItem('theme') === 'dark') {
        document.body.classList.add('dark-mode');
    }
</script>

</body>
</html>