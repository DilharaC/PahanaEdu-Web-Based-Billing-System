<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
<h2>Forgot Password</h2>

<c:if test="${not empty message}">
    <p style="color: green;">${message}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<form action="ForgotPassword" method="post">
    <label for="email">Enter your registered email:</label><br/>
    <input type="email" id="email" name="email" required/><br/><br/>
    <button type="submit">Send Reset Link</button>
</form>

<a href="StaffLogin.jsp">Back to Login</a>
</body>
</html>