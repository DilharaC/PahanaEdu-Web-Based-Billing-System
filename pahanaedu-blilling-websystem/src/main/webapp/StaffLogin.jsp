<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Staff Login</title>

<style>
  /* Reset */
  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #2e7d32, #43a047);
    height: 100vh;
    position: relative;
    color: #2e7d32;
  }

  /* White transparent overlay */
  .overlay {
    position: fixed;
    top: 0; left: 0; right: 0; bottom: 0;
    background: rgba(255, 255, 255, 0.85);
    z-index: 1;
  }

  .login-box {
    position: relative;
    z-index: 2;
    background: white;
    padding: 40px 35px;
    border-radius: 16px;
    width: 320px;
    box-shadow:
      0 8px 24px rgb(0 0 0 / 0.15),
      0 12px 36px rgb(0 0 0 / 0.12);
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
    user-select: none;
  }

  h2 {
    margin: 0 0 28px;
    font-weight: 700;
    font-size: 2rem;
    text-align: center;
    color: #2e7d32;
    text-shadow: 0 1px 3px rgba(67,160,71,0.4);
    letter-spacing: 0.06em;
  }

  form {
    display: flex;
    flex-direction: column;
  }

  input[type="text"],
  input[type="password"] {
    padding: 14px 15px;
    margin-bottom: 20px;
    border: 2px solid #a5d6a7;
    border-radius: 12px;
    font-size: 1rem;
    color: #2e7d32;
    background: #f9fff9;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
  }

  input[type="text"]:focus,
  input[type="password"]:focus {
    outline: none;
    border-color: #43a047;
    box-shadow: 0 0 8px #43a047bb;
  }

  button {
    padding: 14px;
    background: #43a047;
    color: white;
    font-weight: 700;
    font-size: 1.15rem;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    box-shadow: 0 8px 20px rgb(67 160 71 / 0.5);
    transition: background-color 0.3s ease, box-shadow 0.3s ease;
  }

  button:hover {
    background: #2e7d32;
    box-shadow: 0 12px 26px rgb(46 125 50 / 0.7);
  }

  .error {
    margin-top: 18px;
    color: #e74c3c;
    font-weight: 700;
    font-size: 0.9rem;
    text-align: center;
    letter-spacing: 0.02em;
  }

  /* Responsive */
  @media (max-width: 400px) {
    .login-box {
      width: 90vw;
      padding: 30px 25px;
    }
  }
</style>
</head>
<body>
  <div class="overlay"></div>

  <div class="login-box" role="main" aria-label="Staff Login Form">
    <h2>Staff Login</h2>
    <form action="${pageContext.request.contextPath}/StaffLogin" method="post" autocomplete="off">
      <input type="text" name="username" placeholder="Username" required autocomplete="username" aria-label="Username" />
      <input type="password" name="password" placeholder="Password" required autocomplete="current-password" aria-label="Password" />
      <button type="submit" aria-label="Login">Login</button>
    </form>
    <c:if test="${not empty error}">
      <div class="error" role="alert">${error}</div>
    </c:if>
  </div>
</body>
</html>