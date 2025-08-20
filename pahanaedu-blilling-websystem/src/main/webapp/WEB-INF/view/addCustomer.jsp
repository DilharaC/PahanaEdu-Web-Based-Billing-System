<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>

    <!-- Boxicons CDN -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #d4f8d4, #a5d6a7);
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 520px;
            margin: 60px auto;
            background: white;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.08);
            animation: fadeIn 0.5s ease-in-out;
        }
        h1 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
            font-size: 26px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        label {
            font-weight: 600;
            display: block;
            margin: 12px 0 6px;
            color: #2e7d32;
        }
        input[type="text"],
        input[type="email"],
        input[type="tel"],
        textarea,
        select {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #c8e6c9;
            border-radius: 8px;
            background-color: #f9fff9;
            font-size: 15px;
            box-sizing: border-box;
            transition: 0.3s ease;
        }
        input:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #66bb6a;
            box-shadow: 0 0 5px rgba(102,187,106,0.4);
        }
        textarea {
            resize: vertical;
            min-height: 70px;
        }
        .submit-btn {
            background: linear-gradient(135deg, #66bb6a, #43a047);
            color: white;
            font-weight: bold;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: 0.3s ease;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
        }
        .submit-btn:hover {
            background: linear-gradient(135deg, #57a05a, #388e3c);
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .back-link {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            margin-top: 18px;
            color: #2e7d32;
            text-decoration: none;
            font-size: 14px;
            font-weight: bold;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .message {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .error-message {
            background: #ffcdd2;
            color: #b71c1c;
        }
        .success-message {
            background: #c8e6c9;
            color: #1b5e20;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="container">
    <h1><i class='bx bxs-user'></i> Add Customer</h1>

    <!-- ✅ Error / Success Messages -->
    <c:if test="${not empty errorMessage}">
        <div class="message error-message">
            <i class='bx bx-error-circle'></i> ${errorMessage}
        </div>
    </c:if>

    <c:if test="${not empty param.success}">
        <div class="message success-message">
            <i class='bx bx-check-circle'></i> ${param.success}
        </div>
    </c:if>

    <form action="Customer?action=add" method="post">

        <label for="name"><i class='bx bx-user'></i> Full Name</label>
        <input type="text" id="name" name="name" required>

        <label for="email"><i class='bx bx-envelope'></i> Email</label>
        <input type="email" id="email" name="email" required>

        <label for="phone"><i class='bx bx-phone'></i> Mobile Number</label>
        <input type="tel" id="phone" name="phone" pattern="[0-9]{10,15}" required
               title="Please enter a valid phone number with 10 to 15 digits">

        <label for="address"><i class='bx bx-home'></i> Address</label>
        <textarea id="address" name="address"></textarea>

        <label for="active"><i class='bx bx-check-circle'></i> Active</label>
        <select id="active" name="active">
            <option value="true" selected>Yes</option>
            <option value="false">No</option>
        </select>

        <button type="submit" class="submit-btn"><i class='bx bx-plus'></i> Add Customer</button>
    </form>

    <a href="Customer" class="back-link"><i class='bx bx-arrow-back'></i> Back to Customer List</a>
</div>

</body>
</html>
