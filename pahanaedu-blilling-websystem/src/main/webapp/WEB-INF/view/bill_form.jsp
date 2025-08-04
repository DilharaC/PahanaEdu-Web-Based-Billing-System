<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create Bill</title>
    <style>
        /* === Header styling with fixed position === */
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
            z-index: 1000;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            font-weight: bold;
            user-select: none;
            box-sizing: border-box;
        }

    .header h1 {
    font-size:2.1rem; /* smaller font size */
    display: flex;
    align-items: center;
    gap: 6px;
    margin: 0;
}

.header h1 i {
    font-size: 2.1rem; /* smaller icon */
    color: #4caf50; /* keep nice green */
}
        .back-home-btn {
            position: absolute;
            right: 20px;
            top: 50%;
            transform: translateY(-50%);
            background-color: #4caf50;
            color: white;
            padding: 8px 16px;
            border-radius: 5px;
            font-weight: bold;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 6px;
            font-size: 1rem;
            transition: background-color 0.3s ease;
            white-space: nowrap;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
            max-width: calc(100% - 40px);
            overflow: hidden;
            text-overflow: ellipsis;
            z-index: 1100;
        }

        .back-home-btn:hover {
            background-color: #388e3c;
        }

        /* === Body padding so content won't be hidden under fixed header === */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f4fff4;
            color: #333;
            padding-top: 75px; /* header height + some space */
        }
        h2 {
            margin-bottom: 20px;
            color: #388e3c;
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 1.8rem;
        }
        h2 i {
            font-size: 2rem;
            color: #66bb6a;
        }
        .container {
            max-width: 900px;
            margin: 30px auto;
            background: white;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 0 12px rgba(102, 187, 106, 0.2);
        }
        .section {
            border: 1px solid #a5d6a7;
            padding: 15px 20px;
            margin-bottom: 25px;
            border-radius: 6px;
            background-color: #e8f5e9;
        }
        label {
            font-weight: bold;
            color: #2e7d32;
            margin-bottom: 6px;
            display: inline-block;
        }
        input[type=text], input[type=number], select {
            width: 250px;
            padding: 8px 10px;
            margin-top: 6px;
            margin-bottom: 12px;
            border-radius: 4px;
            border: 1px solid #81c784;
            box-sizing: border-box;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }
        input[type=text]:focus, input[type=number]:focus, select:focus {
            border-color: #4caf50;
            outline: none;
        }
        button {
            background-color: #4caf50;
            border: none;
            color: white;
            padding: 8px 18px;
            font-weight: bold;
            font-size: 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-left: 10px;
        }
        button:hover {
            background-color: #388e3c;
        }
        #customerInfo, #productInfo {
            font-weight: 600;
            color: #2e7d32;
            margin-top: 5px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
            font-size: 1rem;
        }
        table, th, td {
            border: 1px solid #a5d6a7;
        }
        th {
            background-color: #66bb6a;
            color: white;
            padding: 12px;
            text-align: center;
        }
        td {
            padding: 10px 12px;
            text-align: center;
            vertical-align: middle;
        }
        tbody tr:hover {
            background-color: #c8e6c9;
        }
        .remove-btn {
            color: #ef5350;
            font-weight: bold;
            cursor: pointer;
            font-size: 1.2rem;
            user-select: none;
            transition: color 0.2s ease;
        }
        .remove-btn:hover {
            color: #b71c1c;
        }
        .summary {
            width: 320px;
            float: right;
            margin-top: 10px;
            font-weight: 600;
            color: #2e7d32;
        }
        .summary td {
            padding: 8px 12px;
            text-align: right;
            font-size: 1.1rem;
        }
        .summary input[type=number] {
            width: 90px;
            font-weight: normal;
            font-size: 1rem;
            border: 1px solid #81c784;
            border-radius: 4px;
            padding: 5px;
        }
        .buttons {
            text-align: center;
            margin-top: 30px;
        }
        .buttons button {
            min-width: 140px;
            margin: 0 12px;
        }
    </style>
    <!-- Optional: Include Boxicons for icons -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<!-- Fixed header with Home button -->
<div class="header">
    <h1><i class='bx bx-receipt'></i> Create Bill</h1>
    <c:choose>
        <c:when test="${sessionScope.role == 'admin'}">
            <a href="${pageContext.request.contextPath}/AdminDashboard?action=dashboard" class="back-home-btn">
                <i class='bx bx-home'></i> Home
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/StaffDashboard?action=dashboard" class="back-home-btn">
                <i class='bx bx-home'></i> Home
            </a>
        </c:otherwise>
    </c:choose>
</div>

<div class="container">
    <h2><i class='bx bx-receipt'></i> Create Bill</h2>

    <form action="Bill" method="post" onsubmit="return prepareForm();">
        <input type="hidden" name="action" value="save">

        <!-- Customer Section -->
        <div class="section">
            <label for="customerSearch"><i class='bx bx-phone'></i> Search Customer by Phone:</label><br>
            <input type="text" id="customerSearch" placeholder="Type phone number...">

            <label for="customerSelect"><i class='bx bx-user'></i> Select Customer:</label><br>
            <select id="customerSelect" name="customerId" onchange="showCustomerInfo()">
                <option value="">-- Select Customer --</option>
                <c:forEach var="c" items="${customers}">
                    <option value="${c.customerId}" data-phone="${c.phone}" data-name="${c.name}">
                        ${c.name} - ${c.phone}
                    </option>
                </c:forEach>
            </select>
            <div id="customerInfo"></div>
        </div>

        <!-- Product Section -->
        <div class="section">
            <label for="productSearch"><i class='bx bx-search'></i> Search Product:</label><br>
            <input type="text" id="productSearch" placeholder="Search by name or category">

            <label for="productSelect"><i class='bx bx-package'></i> Select Product:</label><br>
            <select id="productSelect">
                <option value="">-- Select Product --</option>
                <c:forEach var="p" items="${products}">
                    <option value="${p.productId}" data-price="${p.price}" data-stock="${p.quantity}" data-name="${p.name}" data-category="${p.category}">
                        ${p.name} - $${p.price} (Stock: ${p.quantity}) | Category: ${p.category}
                    </option>
                </c:forEach>
            </select>

            <span id="productInfo"></span>
            <input type="number" id="productQty" placeholder="Qty" min="1" style="width:80px; margin-left:15px;">
            <button type="button" onclick="addItem()"><i class='bx bx-plus'></i> Add</button>
        </div>

        <!-- Bill Items Table -->
        <div class="section">
            <b>BILL ITEMS</b>
            <table id="billTable" aria-label="Bill items table">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Qty</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <tr><td colspan="5">No items added</td></tr>
                </tbody>
            </table>
        </div>

        <!-- Summary -->
        <div class="section" style="overflow: auto;">
            <table class="summary" aria-label="Bill summary">
                <tr>
                    <td>Total:</td>
                    <td id="totalAmount">$0.00</td>
                </tr>
                <tr>
                    <td>Tax (10%):</td>
                    <td id="taxAmount">$0.00</td>
                </tr>
                <tr>
                    <td>Discount:</td>
                    <td><input type="number" id="discount" value="0" min="0" oninput="updateSummary()"></td>
                </tr>
                <tr>
                    <td><b>Final Total:</b></td>
                    <td id="finalTotal"><b>$0.00</b></td>
                </tr>
            </table>
        </div>

        <!-- Action Buttons -->
        <div class="buttons">
            <button type="submit"><i class='bx bx-check-circle'></i> Generate Bill</button>
            <button type="button" onclick="clearBill()"><i class='bx bx-reset'></i> Clear Bill</button>
        </div>
    </form>
</div>

<script>
    let billItems = [];

    // Customer search by phone
    document.getElementById("customerSearch").addEventListener("keyup", function() {
        let searchValue = this.value.toLowerCase();
        let customerSelect = document.getElementById("customerSelect");

        for (let i = 0; i < customerSelect.options.length; i++) {
            let option = customerSelect.options[i];
            if (i === 0) continue; // Skip default option

            let phone = option.dataset.phone ? option.dataset.phone.toLowerCase() : "";

            if (phone.includes(searchValue)) {
                option.style.display = "";
            } else {
                option.style.display = "none";
            }
        }
    });

    function showCustomerInfo() {
        let select = document.getElementById("customerSelect");
        let option = select.options[select.selectedIndex];
        if (option.value) {
            document.getElementById("customerInfo").innerText =
                "Name: " + option.dataset.name + " | Phone: " + option.dataset.phone;
        } else {
            document.getElementById("customerInfo").innerText = "";
        }
    }

    // Product info display
    document.getElementById("productSelect").addEventListener("change", function() {
        let option = this.options[this.selectedIndex];
        if (option.value) {
            document.getElementById("productInfo").innerText =
                "Price: $" + option.dataset.price + " | Stock: " + option.dataset.stock;
        } else {
            document.getElementById("productInfo").innerText = "";
        }
    });

    // Product search filter
    document.getElementById("productSearch").addEventListener("keyup", function() {
        let searchValue = this.value.toLowerCase();
        let productSelect = document.getElementById("productSelect");

        for (let i = 0; i < productSelect.options.length; i++) {
            let option = productSelect.options[i];
            if (i === 0) continue;

            let name = option.dataset.name ? option.dataset.name.toLowerCase() : "";
            let category = option.dataset.category ? option.dataset.category.toLowerCase() : "";

            if (name.includes(searchValue) || category.includes(searchValue)) {
                option.style.display = "";
            } else {
                option.style.display = "none";
            }
        }
    });

    function addItem() {
        let productSelect = document.getElementById("productSelect");
        let qtyInput = document.getElementById("productQty");

        let option = productSelect.options[productSelect.selectedIndex];
        let qty = parseInt(qtyInput.value);

        if (!option.value || isNaN(qty) || qty <= 0) {
            alert("Please select a product and enter a valid quantity.");
            return;
        }

        let stock = parseInt(option.dataset.stock);
        if (qty > stock) {
            alert("Quantity exceeds available stock (" + stock + ").");
            return;
        }

        let existingIndex = billItems.findIndex(item => item.id === option.value);
        if (existingIndex !== -1) {
            if (billItems[existingIndex].qty + qty > stock) {
                alert("Total quantity exceeds available stock (" + stock + ").");
                return;
            }
            billItems[existingIndex].qty += qty;
        } else {
            billItems.push({
                id: option.value,
                name: option.dataset.name,
                price: parseFloat(option.dataset.price),
                qty: qty
            });
        }

        updateTable();
        qtyInput.value = "";
        productSelect.value = "";
        document.getElementById("productInfo").innerText = "";
    }

    function updateTable() {
        let tbody = document.querySelector("#billTable tbody");
        tbody.innerHTML = "";

        if (billItems.length === 0) {
            tbody.innerHTML = "<tr><td colspan='5'>No items added</td></tr>";
            document.getElementById("totalAmount").innerText = "$0.00";
            updateSummary();
            return;
        }

        let total = 0;

        for (let i = 0; i < billItems.length; i++) {
            let item = billItems[i];
            let itemTotal = item.qty * item.price;
            total += itemTotal;

            let row = document.createElement("tr");

            let tdName = document.createElement("td");
            tdName.textContent = item.name;

            let tdQty = document.createElement("td");
            tdQty.textContent = item.qty;

            let tdPrice = document.createElement("td");
            tdPrice.textContent = "$" + item.price.toFixed(2);

            let tdTotal = document.createElement("td");
            tdTotal.textContent = "$" + itemTotal.toFixed(2);

            let tdRemove = document.createElement("td");
            tdRemove.textContent = "X";
            tdRemove.classList.add("remove-btn");
            tdRemove.addEventListener("click", function() {
                billItems.splice(i, 1);
                updateTable();
            });

            row.appendChild(tdName);
            row.appendChild(tdQty);
            row.appendChild(tdPrice);
            row.appendChild(tdTotal);
            row.appendChild(tdRemove);

            tbody.appendChild(row);
        }

        document.getElementById("totalAmount").innerText = "$" + total.toFixed(2);
        updateSummary();
    }

    function updateSummary() {
        let total = parseFloat(document.getElementById("totalAmount").innerText.replace("$", ""));
        let tax = total * 0.10;
        let discount = parseFloat(document.getElementById("discount").value) || 0;
        let finalTotal = total + tax - discount;
        if (finalTotal < 0) finalTotal = 0;

        document.getElementById("taxAmount").innerText = "$" + tax.toFixed(2);
        document.getElementById("finalTotal").innerText = "$" + finalTotal.toFixed(2);
    }

    function clearBill() {
        billItems = [];
        updateTable();
        document.getElementById("discount").value = 0;
        document.getElementById("customerSelect").value = "";
        document.getElementById("customerInfo").innerText = "";
        document.getElementById("productSelect").value = "";
        document.getElementById("productQty").value = "";
        document.getElementById("productInfo").innerText = "";
        document.getElementById("productSearch").value = "";
        document.getElementById("customerSearch").value = "";
    }

    function prepareForm() {
        if (billItems.length === 0) {
            alert("Please add at least one product.");
            return false;
        }

        let form = document.querySelector("form");
        [...form.querySelectorAll('input[name="productId"], input[name="quantity"]')].forEach(el => el.remove());

        billItems.forEach(item => {
            let inputProduct = document.createElement("input");
            inputProduct.type = "hidden";
            inputProduct.name = "productId";
            inputProduct.value = item.id;
            form.appendChild(inputProduct);

            let inputQty = document.createElement("input");
            inputQty.type = "hidden";
            inputQty.name = "quantity";
            inputQty.value = item.qty;
            form.appendChild(inputQty);
        });

        return true;
    }
</script>

</body>
</html>