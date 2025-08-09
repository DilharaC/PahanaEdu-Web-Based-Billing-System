<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Bill Details</title>
    <style>
        /* Reset & base */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f4fff4;
            color: #333;
            padding-top: 75px; /* header height + spacing */
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        /* Fixed header */
        .header {
            background-color: #66bb6a;
            padding: 15px 30px;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            color: white;
            font-size: 1.8rem;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            font-weight: bold;
            user-select: none;
            box-sizing: border-box;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            z-index: 1000;
        }
        .header h1 {
            margin: 0;
            font-size: 2.1rem;
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .header h1 i {
            font-size: 2.1rem;
            color: #4caf50;
        }

        /* Container for content */
        .container {
            max-width: 900px;
            margin: 30px auto 50px;
            background: white;
            padding: 25px 35px;
            border-radius: 8px;
            box-shadow: 0 0 12px rgba(102, 187, 106, 0.2);
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        h2 {
            color: #388e3c;
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 2rem;
            margin-bottom: 25px;
        }
        h2 i {
            font-size: 2.4rem;
            color: #66bb6a;
        }

        p {
            font-size: 1.1rem;
            margin: 8px 0;
            color: #2e7d32;
            font-weight: 600;
        }

        /* Styled table */
        table {
            border-collapse: collapse;
            width: 100%;
            font-size: 1rem;
            margin-top: 15px;
            box-shadow: 0 4px 12px rgba(102, 187, 106, 0.15);
            border-radius: 8px;
            overflow: hidden;
        }
        thead {
            background: linear-gradient(90deg, #66bb6a, #4caf50);
            color: white;
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #c8e6c9;
        }
        tbody tr:hover {
            background-color: #e8f5e9;
            transition: background-color 0.3s ease;
        }

        /* Back button styling */
        .back-btn {
            display: inline-block;
            margin-top: 30px;
            background-color: #4caf50;
            color: white;
            padding: 10px 22px;
            font-weight: bold;
            text-decoration: none;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.2);
            transition: background-color 0.3s ease;
            font-size: 1.1rem;
            user-select: none;
        }
        .back-btn:hover {
            background-color: #388e3c;
        }

        /* Responsive adjustments */
        @media (max-width: 600px) {
            .container {
                padding: 20px;
                margin: 20px 15px 50px;
            }
            th, td {
                padding: 10px 8px;
                font-size: 0.9rem;
            }
            h2 {
                font-size: 1.6rem;
            }
        }

        /* Dark mode compatibility */
        body.dark-mode {
            background-color: #121212;
            color: #ddd;
        }
        body.dark-mode .header {
            background-color: #1b5e20;
            color: #c8e6c9;
        }
        body.dark-mode .container {
            background-color: #1e1e1e;
            color: #ccc;
            box-shadow: 0 0 15px #234d23;
        }
        body.dark-mode thead {
            background: linear-gradient(90deg, #388e3c, #2e7d32);
            color: #e8f5e9;
        }
        body.dark-mode tbody tr:hover {
            background-color: #3e8e41;
        }
        body.dark-mode .back-btn {
            background-color: #2e7d32;
            color: #c8e6c9;
        }
        body.dark-mode .back-btn:hover {
            background-color: #1b5e20;
        }
    </style>
    <!-- Optional: Boxicons for icon -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<div class="header">
    <h1><i class='bx bx-receipt'></i> Bill Details</h1>
</div>

<div class="container" role="main" aria-label="Bill Details Section">
    <h2><i class='bx bx-info-circle'></i> Bill Information</h2>

    <p><strong>Bill ID:</strong> ${bill.billId}</p>
    <p><strong>Bill Date:</strong> ${bill.billDate}</p>
    <p><strong>Customer:</strong> ${bill.customer.name} (${bill.customer.phone})</p>
    <p><strong>Total Amount:</strong> $${bill.totalAmount}</p>

    <h2><i class='bx bx-package'></i> Items</h2>
    <table aria-describedby="itemsTableCaption" aria-label="List of items in the bill">
        <caption id="itemsTableCaption" class="sr-only">Items included in the bill</caption>
        <thead>
            <tr>
                <th scope="col">Product</th>
                <th scope="col">Quantity</th>
                <th scope="col">Price</th>
                <th scope="col">Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${bill.items}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>$${item.price}</td>
                    <td>$${item.total}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="StaffDashboard?action=allTransactions" class="back-btn" role="button" aria-label="Back to Bill List">
        <i class='bx bx-arrow-back'></i> Back to Bill List
    </a>
</div>

<script>
    // Accessibility helper: hide caption visually but keep for screen readers
    (function(){
        const style = document.createElement('style');
        style.innerHTML = `.sr-only {
            position: absolute !important;
            width: 1px !important;
            height: 1px !important;
            padding: 0 !important;
            margin: -1px !important;
            overflow: hidden !important;
            clip: rect(0,0,0,0) !important;
            border: 0 !important;
        }`;
        document.head.appendChild(style);
    })();

    // Dark mode automatic based on localStorage, matching your Create Bill page
    if(localStorage.getItem('theme') === 'dark') {
        document.body.classList.add('dark-mode');
    }
</script>

</body>
</html>
