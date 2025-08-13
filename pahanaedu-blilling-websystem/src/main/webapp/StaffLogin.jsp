<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Staff Login</title>

<!-- Boxicons CDN -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet' />

<style>
  * { box-sizing: border-box; margin: 0; padding: 0; }

  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #1e3c1e, #43a047);
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #2e7d32;
  }

  .login-container {
    background: #ffffff;
    border-radius: 16px;
    width: 360px;
    padding: 40px 35px;
    box-shadow: 0 16px 40px rgba(0,0,0,0.15);
    text-align: center;
    position: relative;
  }

  h2 {
    font-size: 2rem;
    font-weight: 700;
    margin-bottom: 30px;
    color: #2e7d32;
  }

  .icon-top {
    font-size: 48px;
    color: #43a047;
    margin-bottom: 15px;
    display: inline-block;
    transition: transform 0.3s ease;
  }
  .icon-top:hover { transform: scale(1.1); }

  form {
    display: flex;
    flex-direction: column;
    gap: 20px;
    position: relative;
  }

  .input-group {
    position: relative;
  }

  .input-group i {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    color: #43a047;
    font-size: 18px;
    pointer-events: none;
  }

  input[type="text"],
  input[type="password"] {
    width: 100%;
    padding: 14px 16px 14px 42px;
    border: 1px solid #c8e6c9;
    border-radius: 10px;
    font-size: 1rem;
    background: #f9fff9;
    transition: border 0.3s ease, box-shadow 0.3s ease;
  }

  input[type="text"]:focus,
  input[type="password"]:focus {
    border-color: #43a047;
    box-shadow: 0 0 8px rgba(67,160,71,0.4);
    outline: none;
  }

  button.login-btn {
    padding: 14px;
    background: #43a047;
    color: white;
    font-weight: 700;
    font-size: 1.1rem;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background 0.3s ease, transform 0.2s ease;
    margin-bottom: 10px;
  }
  button.login-btn:hover {
    background: #2e7d32;
    transform: translateY(-2px);
  }

  .forgot-password {
    display: inline-block;
    font-size: 0.95rem;
    color: #43a047;
    font-weight: 600;
    text-decoration: none;
    margin-top: 6px;
    transition: color 0.3s ease;
  }
  .forgot-password:hover {
    color: #2e7d32;
  }

  .error {
    margin-top: 18px;
    color: #e74c3c;
    font-weight: 600;
    font-size: 0.9rem;
  }

  @media (max-width: 400px) {
    .login-container { width: 90vw; padding: 30px 20px; }
  }
</style>
</head>
<body>

<div class="login-container" role="main" aria-label="Staff Login Form">
  <i class='bx bx-user-circle icon-top' aria-hidden="true"></i>
  <h2>Staff Login</h2>

  <form action="${pageContext.request.contextPath}/StaffLogin" method="post" autocomplete="off">
    <div class="input-group">
      <i class='bx bx-user'></i>
      <input type="text" name="username" placeholder="Username" required autocomplete="username" aria-label="Username" />
    </div>

    <div class="input-group">
      <i class='bx bx-lock'></i>
      <input type="password" name="password" placeholder="Password" required autocomplete="current-password" aria-label="Password" />
    </div>

    <button type="submit" class="login-btn" aria-label="Login">Login</button>
  </form>

  <a href="${pageContext.request.contextPath}/ForgotPassword" class="forgot-password" aria-label="Forgot Password?">Forgot Password?</a>

  <c:if test="${not empty error}">
    <div class="error" role="alert">${error}</div>
  </c:if>
</div>

</body>
</html>
