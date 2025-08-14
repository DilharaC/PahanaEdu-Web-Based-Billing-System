<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Forgot Password</title>

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

  .forgot-container {
    background: #ffffff;
    border-radius: 16px;
    width: 380px;
    padding: 40px 35px;
    box-shadow: 0 16px 40px rgba(0,0,0,0.15);
    text-align: center;
    position: relative;
  }

  h2 {
    font-size: 2rem;
    font-weight: 700;
    margin-bottom: 20px;
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

  input[type="email"] {
    width: 100%;
    padding: 14px 16px 14px 42px;
    border: 1px solid #c8e6c9;
    border-radius: 10px;
    font-size: 1rem;
    background: #f9fff9;
    transition: border 0.3s ease, box-shadow 0.3s ease;
  }

  input[type="email"]:focus {
    border-color: #43a047;
    box-shadow: 0 0 8px rgba(67,160,71,0.4);
    outline: none;
  }

  button.send-btn {
    padding: 14px;
    background: #43a047;
    color: white;
    font-weight: 700;
    font-size: 1.1rem;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background 0.3s ease, transform 0.2s ease;
  }
  button.send-btn:hover {
    background: #2e7d32;
    transform: translateY(-2px);
  }

  a.back-login {
    display: inline-block;
    margin-top: 15px;
    font-size: 0.95rem;
    color: #43a047;
    font-weight: 600;
    text-decoration: none;
    transition: color 0.3s ease;
  }
  a.back-login:hover { color: #2e7d32; }

  .message {
    margin-bottom: 15px;
    font-weight: 600;
    font-size: 0.9rem;
    color: green;
  }

  .error {
    margin-bottom: 15px;
    font-weight: 600;
    font-size: 0.9rem;
    color: #e74c3c;
  }

  @media (max-width: 420px) {
    .forgot-container { width: 90vw; padding: 30px 20px; }
  }
</style>
</head>
<body>

<div class="forgot-container" role="main" aria-label="Forgot Password Form">
  <i class='bx bx-lock-alt icon-top' aria-hidden="true"></i>
  <h2>Forgot Password</h2>

  <c:if test="${not empty message}">
      <div class="message" role="alert">${message}</div>
  </c:if>
  <c:if test="${not empty error}">
      <div class="error" role="alert">${error}</div>
  </c:if>

  <form action="ForgotPassword" method="post" autocomplete="off">
      <div class="input-group">
          <i class='bx bx-envelope'></i>
          <input type="email" id="email" name="email" placeholder="Enter your registered email" required aria-label="Email" />
      </div>
      <button type="submit" class="send-btn" aria-label="Send Reset Link">Send Reset Link</button>
  </form>

  <a href="StaffLogin.jsp" class="back-login" aria-label="Back to Login"><i class='bx bx-arrow-back'></i> Back to Login</a>
</div>

</body>
</html>
