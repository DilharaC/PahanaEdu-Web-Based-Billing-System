<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Reset Password</title>
</head>
<body>
<h2>Reset Password</h2>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<form action="ForgotPassword" method="post">
    <input type="hidden" name="token" value="${token}"/>

    <label for="newPassword">New Password:</label><br/>
    <input type="password" id="newPassword" name="newPassword" required/><br/><br/>

    <label for="confirmPassword">Confirm New Password:</label><br/>
    <input type="password" id="confirmPassword" name="confirmPassword" required/><br/><br/>

    <button type="submit">Reset Password</button>
</form>

<a href="StaffLogin.jsp">Back to Login</a>
</body>
</html>