<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Audit Log</title>

<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<style>
    body { 
        font-family: Arial, sans-serif; 
        background-color: #f4fff4; 
        margin: 0; 
        padding: 0; 
        color: #000; 
    }

    .header { 
        background-color: #66bb6a; 
        color: white; 
        padding: 15px; 
        display: flex; 
        justify-content: center; 
        align-items: center; 
        gap: 8px; 
        position: relative; 
    }

    .header h1 { 
        font-size: 1.8rem; 
        margin: 0; 
        flex-grow: 1; 
        text-align: center; 
        display: flex; 
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
        color: #000; 
    }

    .sidebar h3 { 
        color: #388e3c; 
        display: flex; 
        align-items: center; 
        gap: 6px; 
        margin-bottom: 10px; 
    }

    .content { 
        flex: 1; 
        background: white; 
        padding: 15px; 
        border: 1px solid #ccc; 
        border-radius: 5px; 
        color: #000; 
    }

    .content-header { 
        display: flex; 
        justify-content: space-between; 
        margin-bottom: 15px; 
        align-items: center; 
    }

    /* Search bar and dropdown styling */
    .form-control, select {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border-radius: 4px;
        border: 1px solid #ccc;
        box-sizing: border-box;
        font-size: 1rem;
    }

    .search-btn { 
        background-color: #4caf50; 
        color: white; 
        border: none; 
        padding: 8px 12px; 
        border-radius: 4px; 
        cursor: pointer; 
        width: 100%; 
        font-size: 1rem;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 6px;
    }

    .search-btn:hover { 
        background-color: #3e8e41; 
    }

    table { 
        width: 100%; 
        border-collapse: collapse; 
        color: #000; 
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
</style>
</head>
<body>

<div class="header">
    <h1><i class='bx bx-file'></i> Audit Log</h1>
    <a href="${pageContext.request.contextPath}/AdminDashboard?action=dashboard" class="back-home-btn"><i class='bx bx-home'></i> Home</a>
</div>

<div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
        <h3><i class='bx bx-filter'></i> Search & Filter</h3>
        <form method="get" action="${pageContext.request.contextPath}/AdminDashboard">
            <input type="hidden" name="action" value="auditLog" />
            <input type="text" name="search" value="${param.search}" placeholder="Search by action or staff..." class="form-control" />
            <select name="category" class="form-control">
                <option value="">All Categories</option>
                <option value="bill" ${param.category == 'bill' ? 'selected' : ''}>Bill</option>
                <option value="customer" ${param.category == 'customer' ? 'selected' : ''}>Customer</option>
                <option value="product" ${param.category == 'product' ? 'selected' : ''}>Product</option>
            </select>
            <button type="submit" class="search-btn"><i class='bx bx-search'></i> Search</button>
        </form>
    </div>

    <!-- Content -->
    <div class="content">
        <div class="content-header">
            <h2><i class='bx bx-list-ul'></i> Audit Records</h2>
        </div>

        <table id="auditTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Staff</th>
                    <th>Action</th>
                    <th>Target Entity</th>
                    <th>Target ID</th>
                    <th>Details</th>
                    <th>Timestamp</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="log" items="${auditLogs}">
                    <tr>
                        <td>${log.id}</td>
                        <td>${log.performedBy}</td>
                        <td>${log.action}</td>
                        <td>${log.targetEntity}</td>
                        <td>${log.targetId}</td>
                        <td>${log.details}</td>
                        <td>${log.timestamp}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty auditLogs}">
                    <tr>
                        <td colspan="7" style="text-align:center; font-style: italic; color: #888;">No audit records found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<script>
    // Sort table by Timestamp (last one first)
    window.addEventListener('DOMContentLoaded', () => {
        const table = document.getElementById('auditTable');
        const tbody = table.tBodies[0];
        const rows = Array.from(tbody.rows);

        rows.sort((a, b) => {
            const dateA = new Date(a.cells[6].innerText); // Timestamp column
            const dateB = new Date(b.cells[6].innerText);
            return dateB - dateA; // descending order (latest first)
        });

        rows.forEach(row => tbody.appendChild(row));
    });
</script>

</body>
</html>
