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
        transition: background-color 0.3s ease, color 0.3s ease;
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
        transition: background-color 0.3s ease, border-color 0.3s ease;
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
        overflow-x: auto;
        transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease;
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
        vertical-align: middle;
        transition: color 0.3s ease;
    }
    tr:hover {
        background-color: #f1f8f1;
        transition: background-color 0.3s ease;
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
        user-select: none;
        transition: background-color 0.3s ease;
    }
    .btn-view:hover {
        background-color: #1e88e5;
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
    body.dark-mode .btn-view {
        background-color: #2196f3;
        color: #e3f2fd;
    }
    body.dark-mode .btn-view:hover {
        background-color: #1565c0;
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
   <form method="get" action="StaffDashboard?action=allTransactions">
        <label for="customerPhone">Customer Phone</label>
        <input type="text" id="customerPhone" name="customerPhone" value="${customerPhone}" placeholder="Customer phone" class="form-control" />

        <label for="startDate">Start Date</label>
        <input type="date" id="startDate" name="startDate" value="${startDate}" class="form-control" />

        <label for="endDate">End Date</label>
        <input type="date" id="endDate" name="endDate" value="${endDate}" class="form-control" />

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
            <th><i class='bx bx-phone'></i> Phone</th>  <!-- Added phone column -->
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
                        <td>${bill.customer.phone}</td>  <!-- Display phone -->
                        <td>${bill.totalAmount}</td>
                        <td>
                            <a href="Bill?action=view&billId=${bill.billId}" class="btn-view" aria-label="View bill ${bill.billId}">
                                <i class='bx bx-show'></i> View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="6" style="text-align:center; font-style: italic; color: #888;">
                        No transactions found.
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
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