<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Change Password</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4fff4;
        padding: 30px;
    }
    .container {
        max-width: 400px;
        margin: auto;
        background: white;
        padding: 25px;
        border-radius: 8px;
        box-shadow: 0 3px 10px rgba(0,0,0,0.1);
    }
    h2 {
        text-align: center;
        color: #388e3c;
        margin-bottom: 20px;
    }
    label {
        display: block;
        margin-bottom: 6px;
        font-weight: 600;
    }
    input[type="password"] {
        width: 100%;
        padding: 8px 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        font-size: 1rem;
    }
    .btn-submit {
        width: 100%;
        background-color: #4caf50;
        border: none;
        padding: 12px;
        color: white;
        font-weight: bold;
        font-size: 1.1rem;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
    .btn-submit:hover {
        background-color: #388e3c;
    }
    .message {
        margin-bottom: 15px;
        padding: 10px;
        border-radius: 4px;
        font-weight: 600;
    }
    .error {
        background-color: #f8d7da;
        color: #721c24;
    }
    .success {
        background-color: #d4edda;
        color: #155724;
    }
    /* Home Button Style */
    .btn-home {
        display: inline-block;
        margin-bottom: 15px;
        padding: 8px 16px;
        background-color: #4caf50;
        color: white;
        text-decoration: none;
        font-weight: bold;
        border-radius: 4px;
        transition: background-color 0.3s ease;
    }
    .btn-home:hover {
        background-color: #388e3c;
    }
</style>
</head>
<body>

<div class="container">

    <!-- Home Button -->
    <a href="StaffDashboard" class="btn-home">🏠 Home</a>

    <h2>Change Password</h2>

    <!-- Show error message -->
    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>

    <!-- Show success message -->
    <c:if test="${not empty success}">
        <div class="message success">${success}</div>
    </c:if>

    <form action="StaffLogin?action=changePassword" method="post">
        <label for="currentPassword">Current Password</label>
        <input type="password" id="currentPassword" name="currentPassword" required />

        <label for="newPassword">New Password</label>
        <input type="password" id="newPassword" name="newPassword" required />

        <label for="confirmPassword">Confirm New Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required />

        <button type="submit" class="btn-submit">Change Password</button>
    </form>
</div>

</body>
</html>