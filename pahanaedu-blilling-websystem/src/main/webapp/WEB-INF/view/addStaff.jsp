<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Staff</title>
<style>
  body { font-family: Arial; background: #f2f2f2; }
  .container { width: 400px; margin: 50px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 8px 16px rgba(0,0,0,0.2); }
  h2 { text-align: center; color: #333; margin-bottom: 20px; }
  input, select { width: 100%; padding: 10px; margin: 10px 0; border-radius: 5px; border: 1px solid #ccc; }
  button { width: 100%; padding: 12px; background: #43a047; color: white; border: none; border-radius: 5px; cursor: pointer; }
  button:hover { background: #2e7d32; }
  .error { color: red; font-size: 0.9rem; }
  .success { color: green; font-size: 0.9rem; }
</style>
</head>
<body>
<div class="container">
    <h2>Add Staff</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/Staff" method="post">
        <input type="hidden" name="action" value="add" />
        <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />
        <input type="text" name="fullName" placeholder="Full Name" required />
        <input type="email" name="email" placeholder="Email" required />
        <input type="text" name="phone" placeholder="Phone" />
        <input type="text" name="nic" placeholder="NIC" />
        <input type="text" name="jobTitle" placeholder="Job Title" />
        <select name="role" required>
            <option value="">Select Role</option>
            <option value="admin">Admin</option>
            <option value="staff">Staff</option>
        </select>
        <select name="status" required>
            <option value="active">Active</option>
            <option value="inactive">Inactive</option>
        </select>

        <button type="submit">Add Staff</button>
    </form>
</div>
</body>
</html>
