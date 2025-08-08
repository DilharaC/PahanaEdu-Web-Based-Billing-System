<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Transaction History</title>

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
        position: relative;
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
        overflow-x: auto;
    }
    .content-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 15px;
        align-items: center;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        min-width: 700px;
    }
    th {
        background-color: #a5d6a7;
        padding: 10px;
        text-align: left;
    }
    td {
        padding: 8px;
        border-bottom: 1px solid #ddd;
        vertical-align: middle;
    }
    tr:hover {
        background-color: #f1f8f1;
    }
    .btn-view {
        background-color: #42a5f5;
        color: white;
        padding: 5px 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 4px;
        text-decoration: none;
    }
</style>
</head>
<body>

<div class="header">
    <h1><i class='bx bx-receipt'></i> Transaction History</h1>
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
    <!-- Sidebar: filters -->
    <div class="sidebar">
        <h3><i class='bx bx-filter'></i> Filter Transactions</h3>
        <form method="get" action="TransactionHistory">
            <label for="customerName">Customer Name</label>
            <input type="text" id="customerName" name="customerName" value="${param.customerName}" placeholder="Customer name" class="form-control" />

            <label for="startDate">Start Date</label>
            <input type="date" id="startDate" name="startDate" value="${param.startDate}" class="form-control" />

            <label for="endDate">End Date</label>
            <input type="date" id="endDate" name="endDate" value="${param.endDate}" class="form-control" />

            <button type="submit" class="search-btn"><i class='bx bx-search'></i> Search</button>
        </form>
    </div>

    <!-- Content: transaction list -->
    <div class="content">
        <div class="content-header">
            <h2><i class='bx bx-list-ul'></i> All Transactions</h2>
        </div>

        <table>
           <thead>
    <tr>
        <th><i class='bx bx-hash'></i> Bill ID</th>
        <th><i class='bx bx-calendar'></i> Date</th>
        <th><i class='bx bxs-user'></i> Customer</th>
        <th><i class='bx bx-money'></i> Total Amount (Rs)</th>
        <th><i class='bx bx-cog'></i> Actions</th>
    </tr>
</thead>
<tbody>
    <c:choose>
        <c:when test="${not empty allBills}">
            <c:forEach var="bill" items="${allBills}">
                <tr>
                    <td>${bill.billId}</td>
                    <td><fmt:formatDate value="${bill.billDate}" pattern="dd MMM yyyy" /></td>
                    <td>${bill.customer.name}</td>
                    <td>${bill.totalAmount}</td>
                    <td>
                        <a href="Bill?action=view&billId=${bill.billId}" class="btn-view">
                            <i class='bx bx-show'></i> View
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5" style="text-align:center; font-style: italic; color: #888;">
                    No transactions found.
                </td>
            </tr>
        </c:otherwise>
    </c:choose>
</tbody>
        </table>
    </div>
</div>

</body>
</html>