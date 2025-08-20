<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Staff</title>
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
        input[type="password"],
        input[type="email"],
        input[type="tel"],
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
        select:focus {
            outline: none;
            border-color: #66bb6a;
            box-shadow: 0 0 6px rgba(102,187,106,0.4);
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
    <form action="Staff?action=update" method="post">
        <h1><i class='bx bx-edit'></i> Edit Staff</h1>

        <!-- Hidden field for staff ID -->
        <input type="hidden" name="staffId" value="${staff.staffId}" />

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="${staff.username}" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Enter new password">

        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" value="${staff.fullName}" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${staff.email}" required>

        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone" value="${staff.phone}" required>

        <label for="nic">NIC:</label>
        <input type="text" id="nic" name="nic" value="${staff.nic}" required>

        <label for="jobTitle">Job Title:</label>
        <input type="text" id="jobTitle" name="jobTitle" value="${staff.jobTitle}" required>

        <label for="role">Role:</label>
        <select id="role" name="role" required>
            <option value="admin" <c:if test="${staff.role == 'admin'}">selected</c:if>>Admin</option>
            <option value="staff" <c:if test="${staff.role == 'staff'}">selected</c:if>>Staff</option>
        </select>

        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="active" <c:if test="${staff.status == 'active'}">selected</c:if>>Active</option>
            <option value="inactive" <c:if test="${staff.status == 'inactive'}">selected</c:if>>Inactive</option>
        </select>

        <button type="submit" class="submit-btn"><i class='bx bx-save'></i> Update Staff</button>
    </form>

    <a href="Staff?action=list" class="back-link"><i class='bx bx-arrow-back'></i> Back to Staff List</a>
</body>
</html>
