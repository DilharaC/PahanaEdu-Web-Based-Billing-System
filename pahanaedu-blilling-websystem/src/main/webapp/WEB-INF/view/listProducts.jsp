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
    }
    .back-home-btn:hover {
        background-color: #3e8e41;
    }
    .container {
        display: flex;
        gap: 20px;
        padding: 20px;
        align-items: flex-start;
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
    }
    .form-control {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border-radius: 4px;
        border: 1px solid #ccc;
        box-sizing: border-box;
    }
    .sidebar h3 {
        color: #388e3c;
        display: flex;
        align-items: center;
        gap: 6px;
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
    }
    .add-btn:hover {
        background-color: #3e8e41;
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th {
        background-color: #a5d6a7;
        padding: 10px;
        text-align: left;
    }
    td {
        padding: 8px;
        border-bottom: 1px solid #ddd;
    }
    tr:hover {
        background-color: #f1f8f1;
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

</body>
</html>