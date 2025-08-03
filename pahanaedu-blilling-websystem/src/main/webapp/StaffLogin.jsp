<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Staff Login</title>
<style>
    body { font-family: Arial; background: #f4fff4; display: flex; justify-content: center; align-items: center; height: 100vh; }
    .login-box { background: white; padding: 25px; border-radius: 6px; width: 300px; box-shadow: 0px 0px 6px rgba(0,0,0,0.1); }
    h2 { text-align: center; color: #388e3c; }
    input { width: 100%; padding: 8px; margin: 8px 0; border-radius: 4px; border: 1px solid #ccc; }
    button { width: 100%; padding: 10px; background: #4caf50; border: none; color: white; font-weight: bold; cursor: pointer; border-radius: 4px; }
    button:hover { background: #3e8e41; }
    .error { color: red; font-size: 0.9rem; text-align: center; }
</style>
</head>
<body>
<div class="login-box">
    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/StaffLogin" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
</div>
</body>
</html>