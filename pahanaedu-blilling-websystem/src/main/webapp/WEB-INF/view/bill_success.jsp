<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Bill Created Successfully</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background: #f5f5f5; }
        .invoice-container { max-width: 700px; background: white; padding: 20px; margin: auto; border: 1px solid #ccc; border-radius: 8px; }
        .header { text-align: center; border-bottom: 2px solid #000; padding-bottom: 10px; margin-bottom: 20px; }
        .header h1 { margin: 0; font-size: 26px; }
        .header p { margin: 3px 0; }
        .bill-info, .customer-info { margin-bottom: 15px; }
        .bill-info strong, .customer-info strong { display: inline-block; width: 120px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        table, th, td { border: 1px solid #bbb; padding: 8px; }
        th { background-color: #f0f0f0; }
        tfoot td { font-weight: bold; }
        .success-msg { text-align: center; color: green; font-size: 18px; margin-bottom: 15px; }
        .print-btn { display: block; margin: 20px auto 0; background: #28a745; color: white; padding: 10px 15px; text-decoration: none; border-radius: 6px; text-align: center; width: 120px; }
        .print-btn:hover { background: #218838; }
    </style>
</head>
<body>

<div class="invoice-container">
    <div class="header">
        <h1>Pahana Edu Bookshop</h1>
        <p>No. 123, Main Street, Colombo</p>
        <p>Tel: 011-1234567</p>
    </div>

    <p class="success-msg">Bill Created Successfully!</p>

    <div class="bill-info">
        <p><strong>Bill ID:</strong> ${bill.billId}</p>
        <p><strong>Date:</strong> <fmt:formatDate value="${bill.billDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
      <p><strong>Staff ID:</strong> ${sessionScope.staff.staffId}</p>
<p><strong>Staff Name:</strong> ${sessionScope.staff.fullName}</p>
    </div>

    <div class="customer-info">
        <p><strong>Customer:</strong> ${bill.customer.name}</p>
        <p><strong>Phone:</strong> ${bill.customer.phone}</p>
    </div>

    <table>
        <thead>
            <tr>
                <th>Product</th>
                <th>Qty</th>
                <th>Price (Rs.)</th>
                <th>Total (Rs.)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${bill.items}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price}</td>
                    <td>${item.total}</td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" style="text-align: right;">Total Amount:</td>
                <td>Rs. ${bill.totalAmount}</td>
            </tr>
        </tfoot>
    </table>

    <a href="#" class="print-btn" onclick="window.print()">Print Bill</a>
</div>

</body>
</html>