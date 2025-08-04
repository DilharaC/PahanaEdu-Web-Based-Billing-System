<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
    <h2>Forgot Password</h2>

    <!-- Success message -->
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>

    <!-- Error message -->
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="forgot-password?action=submitForm" method="post">
        <label>Email:</label><br/>
        <input type="email" name="email" required placeholder="your-email@example.com" /><br/><br/>
        <button type="submit">Send Reset Link</button>
    </form>
</body>
</html>