<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Admin Dashboard</title>

<!-- Boxicons CDN for icons -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet' />


<style>
  /* Reset */
  * {
    box-sizing: border-box;
  }

  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: #f4f7f6;
    margin: 0;
    padding: 0;
    color: #344d41;
    transition: background 0.3s ease, color 0.3s ease;
  }

  /* Dark Mode Styles */
  body.dark-mode {
    background: #121212;
    color: #e0e0e0;
  }

  body.dark-mode header {
    background: linear-gradient(90deg, #1b5e20, #2e7d32);
    box-shadow: 0 4px 10px rgb(30 120 30 / 0.7);
  }

  /* Header */
  header {
    background: linear-gradient(90deg, #2e7d32, #43a047);
    color: #fff;
    padding: 20px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 4px 10px rgb(67 160 71 / 0.3);
    position: sticky;
    top: 0;
    z-index: 100;
    user-select: none;
  }

  header h1 {
    font-weight: 700;
    font-size: 1.8rem;
    letter-spacing: 1.1px;
    margin: 0;
    text-shadow: 0 2px 6px rgba(0,0,0,0.3);
  }

  /* Dark mode toggle button */
  #themeToggleBtn {
    background: transparent;
    border: none;
    color: white;
    font-size: 24px;
    cursor: pointer;
    margin-right: 20px;
    user-select: none;
    transition: color 0.3s ease, transform 0.3s ease;
  }
  #themeToggleBtn:hover {
    color: #c8e6c9;
    transform: rotate(20deg);
  }
  #themeToggleBtn:active {
    transform: rotate(-20deg);
  }

  /* Profile */
  .profile {
    display: flex;
    align-items: center;
    gap: 12px;
    cursor: pointer;
    position: relative;
    user-select: none;
    transition: transform 0.2s ease;
  }
  .profile:hover {
    transform: scale(1.05);
  }
  .profile img {
    width: 44px;
    height: 44px;
    border-radius: 50%;
    object-fit: cover;
    border: 2px solid #a5d6a7;
    box-shadow: 0 0 8px #a5d6a7aa;
    transition: box-shadow 0.3s ease;
  }
  .profile:hover img {
    box-shadow: 0 0 14px #43a047cc;
  }
  .profile-name {
    font-weight: 600;
    font-size: 1.05rem;
    color: #e8f5e9;
    text-shadow: 0 1px 3px rgba(0,0,0,0.3);
  }

  /* Profile Sidebar (Glass Effect) */
  .profile-sidebar {
    position: fixed;
    top: 0;
    right: -340px; /* hidden by default */
    width: 340px;
    height: 100vh;
    background: rgba(255, 255, 255, 0.15);
    backdrop-filter: blur(12px);
    -webkit-backdrop-filter: blur(12px);
    box-shadow: -5px 0 30px rgba(0,0,0,0.15);
    padding: 30px 25px;
    box-sizing: border-box;
    transition: right 0.35s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 1500;
    overflow-y: auto;
    border-radius: 8px 0 0 8px;
    color: #2e7d32;
    font-weight: 600;
  }
  .profile-sidebar.open {
    right: 0;
  }
  .profile-sidebar h2 {
    margin-top: 0;
    font-weight: 700;
    font-size: 1.6rem;
    margin-bottom: 20px;
    color: #2e7d32;
    text-shadow: 0 1px 4px #a5d6a7cc;
  }
  .profile-sidebar p {
    font-size: 1rem;
    margin: 12px 0;
    color: #1b5e20dd;
  }
  .profile-sidebar hr {
    border: none;
    border-top: 1px solid #a5d6a788;
    margin: 20px 0;
  }
  .profile-sidebar .close-btn {
    background: none;
    border: none;
    font-size: 30px;
    font-weight: 700;
    position: absolute;
    top: 15px;
    right: 15px;
    cursor: pointer;
    color: #388e3c;
    padding: 5px;
    border-radius: 50%;
    transition: background 0.25s ease;
    user-select: none;
  }
  .profile-sidebar .close-btn:hover {
    background: rgba(0,0,0,0.07);
  }
  .profile-sidebar .btn-link {
    display: inline-block;
    margin: 14px 0;
    color: #43a047;
    font-weight: 700;
    font-size: 1rem;
    text-decoration: none;
    cursor: pointer;
    transition: color 0.3s ease;
    user-select: none;
  }
  .profile-sidebar .btn-link:hover {
    color: #2e7d32;
    text-decoration: underline;
  }

  /* Container */
  .container {
    max-width: 1100px;
    margin: 40px auto 60px;
    padding: 0 25px;
  }

  .container h1 {
    color: #2e7d32;
    font-weight: 700;
    font-size: 2rem;
    margin-bottom: 30px;
    text-shadow: 0 2px 6px rgba(67,160,71,0.35);
  }

  /* Stats Cards */
  .stats {
    display: flex;
    gap: 25px;
    margin-bottom: 50px;
    flex-wrap: wrap;
  }
  .stat-card {
    background: white;
    flex: 1 1 30%;
    min-width: 220px;
    padding: 25px 35px;
    border-radius: 16px;
    box-shadow:
      0 6px 12px rgb(0 0 0 / 0.07),
      0 12px 24px rgb(0 0 0 / 0.06);
    display: flex;
    align-items: center;
    gap: 25px;
    transition: box-shadow 0.3s ease, transform 0.25s ease;
    cursor: default;
    user-select: none;
  }
  .stat-card:hover {
    box-shadow:
      0 12px 22px rgb(0 0 0 / 0.12),
      0 18px 36px rgb(0 0 0 / 0.1);
    transform: translateY(-5px);
  }
  .stat-card i {
    font-size: 54px;
    color: #43a047;
    flex-shrink: 0;
    filter: drop-shadow(0 1px 1px rgba(67,160,71,0.35));
  }
  .stat-info h2 {
    margin: 0;
    font-size: 40px;
    color: #2e7d32;
    font-weight: 700;
    letter-spacing: 0.02em;
  }
  .stat-info p {
    margin: 6px 0 0;
    font-weight: 600;
    color: #555;
    font-size: 1.15rem;
  }

  /* Dashboard Links */
  .dashboard-links {
    display: flex;
    gap: 25px;
    flex-wrap: wrap;
    margin-bottom: 50px;
  }
  .dashboard-link {
    background: #43a047;
    color: white;
    padding: 18px 28px;
    border-radius: 14px;
    font-weight: 700;
    font-size: 1.15rem;
    display: flex;
    align-items: center;
    gap: 14px;
    text-decoration: none;
    box-shadow:
      0 6px 18px rgb(67 160 71 / 0.3),
      0 4px 8px rgb(0 0 0 / 0.1);
    transition: background 0.3s ease, box-shadow 0.3s ease, transform 0.2s ease;
    flex: 1 1 220px;
    justify-content: center;
    user-select: none;
  }
  .dashboard-link:hover {
    background: #2e7d32;
    box-shadow:
      0 9px 25px rgb(46 125 50 / 0.6),
      0 6px 12px rgb(0 0 0 / 0.15);
    transform: translateY(-3px);
  }
  .dashboard-link i {
    font-size: 24px;
  }

  /* Last 5 Transactions Table */
  h2.transactions-title {
    color: #2e7d32;
    margin-bottom: 20px;
    font-weight: 700;
    font-size: 1.8rem;
    text-shadow: 0 1px 4px rgba(46,125,50,0.5);
  }

  .table-wrapper {
    overflow-x: auto;
    border-radius: 12px;
    box-shadow:
      0 12px 30px rgb(0 0 0 / 0.12),
      0 4px 15px rgb(0 0 0 / 0.06);
    background: white;
    transition: box-shadow 0.3s ease;
  }

  table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0 8px;
    min-width: 720px;
  }

  thead tr {
    background: linear-gradient(90deg, #43a047, #2e7d32);
    color: white;
    border-radius: 8px;
  }
  thead th {
    padding: 14px 20px;
    font-weight: 700;
    text-align: left;
    letter-spacing: 0.05em;
  }
  tbody tr {
    background: #f9fff9;
    box-shadow:
      0 4px 6px rgb(0 0 0 / 0.07);
    border-radius: 12px;
    transition: background-color 0.25s ease, transform 0.15s ease;
    cursor: default;
  }
  tbody tr:hover {
    background-color: #d0f0c0;
    transform: translateX(6px);
  }
  tbody td {
    padding: 14px 20px;
    font-weight: 600;
    color: #333;
    vertical-align: middle;
  }
  tbody td:nth-child(5) {
    font-weight: 700;
    color: #388e3c;
  }
  tbody tr:last-child td {
    border-bottom: none;
  }

  /* Help Section */
  .help-section {
    background: white;
    padding: 24px 30px;
    border-radius: 14px;
    text-align: center;
    box-shadow:
      0 10px 30px rgb(0 0 0 / 0.08);
    font-size: 1rem;
    color: #444;
    user-select: none;
    transition: box-shadow 0.3s ease;
  }
  .help-section a {
    text-decoration: none;
    color: #43a047;
    font-weight: 700;
    transition: color 0.3s ease;
  }
  .help-section a:hover {
    color: #2e7d32;
    text-decoration: underline;
  }

  /* Responsive */
  @media (max-width: 800px) {
    .stats {
      flex-direction: column;
    }
    .stat-card {
      min-width: auto;
      width: 100%;
    }
    .dashboard-links {
      flex-direction: column;
    }
    .dashboard-link {
      width: 100%;
    }
    table {
      min-width: 100%;
    }
  }

  /* Dark mode specific tweaks */
  body.dark-mode .profile-sidebar {
    background: rgba(30, 30, 30, 0.7);
    color: #c8e6c9;
    box-shadow: -5px 0 30px rgba(0,0,0,0.8);
  }
  body.dark-mode .profile-sidebar h2 {
    color: #a5d6a7;
    text-shadow: 0 1px 3px #2e7d32cc;
  }
  body.dark-mode .profile-sidebar p {
    color: #c8e6c9cc;
  }
  body.dark-mode .profile-sidebar .btn-link {
    color: #81c784;
  }
  body.dark-mode .profile-sidebar .btn-link:hover {
    color: #a5d6a7;
  }
  body.dark-mode .stat-card {
    background: #1e1e1e;
    box-shadow:
      0 6px 12px rgb(0 0 0 / 0.6),
      0 12px 24px rgb(0 0 0 / 0.5);
    color: #ddd;
  }
  body.dark-mode .stat-card i {
    color: #81c784;
    filter: drop-shadow(0 1px 1px #81c784cc);
  }
  body.dark-mode .stat-info h2 {
    color: #a5d6a7;
  }
  body.dark-mode .stat-info p {
    color: #ccc;
  }
  body.dark-mode .dashboard-link {
    background: #2e7d32;
    color: #a5d6a7;
    box-shadow:
      0 6px 18px rgb(30 120 30 / 0.5),
      0 4px 8px rgb(0 0 0 / 0.4);
  }
  body.dark-mode .dashboard-link:hover {
    background: #1b5e20;
    box-shadow:
      0 9px 25px rgb(20 80 20 / 0.7),
      0 6px 12px rgb(0 0 0 / 0.5);
  }
  body.dark-mode .dashboard-link i {
    color: #c8e6c9;
  }
  body.dark-mode .table-wrapper {
    background-color: #121212;
    box-shadow:
      0 12px 30px rgb(0 0 0 / 0.8);
  }
  body.dark-mode table {
    background-color: #121212;
    color: #e0e0e0;
  }
  body.dark-mode thead tr {
    background: #2e7d32;
    color: #c8e6c9;
  }
  body.dark-mode tbody tr {
    background-color: #1e1e1e;
    box-shadow: 0 4px 6px rgb(0 0 0 / 0.8);
  }
  body.dark-mode tbody tr:hover {
    background-color: #388e3c;
    color: #fff;
    transform: translateX(6px);
  }
  body.dark-mode tbody td:nth-child(5) {
    color: #a5d6a7;
  }
  body.dark-mode .help-section {
    background: #1e1e1e;
    color: #ccc;
    box-shadow: 0 10px 30px rgb(0 0 0 / 0.5);
  }
  body.dark-mode .help-section a {
    color: #81c784;
  }
  body.dark-mode .help-section a:hover {
    color: #a5d6a7;
  }
</style>

<script>



  // Data arrays from backend (unchanged)
  const topProductNames = [
    <c:forEach var="name" items="${topProductNames}" varStatus="status">
      "${name}"<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  const topProductQuantities = [
    <c:forEach var="qty" items="${topProductQuantities}" varStatus="status">
      ${qty}<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  const monthlyCustomerGrowth = [
    <c:forEach var="num" items="${monthlyCustomerGrowth}" varStatus="status">
      ${num}<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  const monthlyLabels = [
    <c:forEach var="label" items="${monthlyLabels}" varStatus="status">
      "${label}"<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
      window.location.href = "Logout";
    }
  }

  function toggleProfileSidebar() {
    const sidebar = document.getElementById('profileSidebar');
    sidebar.classList.toggle('open');
  }

  // Close sidebar if clicked outside
  document.addEventListener("click", function (event) {
    const sidebar = document.getElementById('profileSidebar');
    const profileBtn = document.querySelector(".profile");

    if (!sidebar.contains(event.target) && !profileBtn.contains(event.target)) {
      sidebar.classList.remove("open");
    }
  });

  // Dark mode toggle handling with smooth icon animation
  document.addEventListener('DOMContentLoaded', () => {
    const themeToggleBtn = document.getElementById('themeToggleBtn');
    const body = document.body;
    const themeIcon = themeToggleBtn.querySelector('i');

    // Load saved theme
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      body.classList.add('dark-mode');
      themeIcon.className = 'bx bx-sun';
    } else {
      themeIcon.className = 'bx bx-moon';
    }

    // Animate icon rotation on toggle
    themeToggleBtn.addEventListener('click', () => {
      themeToggleBtn.style.transition = 'transform 0.4s ease';
      themeToggleBtn.style.transform = 'rotate(360deg)';
      setTimeout(() => {
        themeToggleBtn.style.transform = 'rotate(0deg)';
      }, 400);

      body.classList.toggle('dark-mode');
      if (body.classList.contains('dark-mode')) {
        themeIcon.className = 'bx bx-sun';
        localStorage.setItem('theme', 'dark');
      } else {
        themeIcon.className = 'bx bx-moon';
        localStorage.setItem('theme', 'light');
      }
    });
  });

 
</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<header>
  <div><h1>Admin Dashboard</h1></div>

  <div style="display:flex; align-items:center; gap: 20px;">
    <!-- Dark/Light Mode Toggle Button -->
    <button id="themeToggleBtn" aria-label="Toggle dark mode" title="Toggle dark mode" tabindex="0">
      <i class='bx bx-moon'></i>
    </button>

    <div class="profile" title="Profile Menu" onclick="toggleProfileSidebar()" tabindex="0" role="button" aria-label="Toggle profile sidebar">
      <img src="https://i.pravatar.cc/40" alt="Profile Picture" />
      <span class="profile-name">${sessionScope.fullName}</span>
      <i class='bx bx-chevron-down' style="color: white;"></i>
    </div>
  </div>
</header>

<!-- Profile Sidebar -->
<div id="profileSidebar" class="profile-sidebar" role="complementary" aria-label="Profile Sidebar">
  <button onclick="toggleProfileSidebar()" class="close-btn" title="Close Profile" aria-label="Close profile sidebar">&times;</button>
  <h2>Staff Profile</h2>
  <p><strong>Name:</strong> ${sessionScope.fullName}</p>
  <p><strong>Username:</strong> ${sessionScope.staff.username}</p>
  <p><strong>Email:</strong> ${sessionScope.staff.email}</p>
  <p><strong>Phone:</strong> ${sessionScope.staff.phone}</p>
  <p><strong>NIC:</strong> ${sessionScope.staff.nic}</p>
  <p><strong>Role:</strong> ${sessionScope.role}</p>
  <hr/>
  <a href="StaffLogin?action=changePassword" class="btn-link">Change Password</a><br/>
  <a href="javascript:void(0);" onclick="confirmLogout();" class="btn-link">Logout</a>
</div>

<div class="container" role="main">
  <h1>Welcome, <span style="color:#388e3c;">${sessionScope.fullName}</span>!</h1>

  <section class="stats" aria-label="Summary statistics">
    <article class="stat-card" title="Total number of products" tabindex="0" aria-live="polite" aria-atomic="true">
      <i class='bx bx-box' aria-hidden="true"></i>
      <div class="stat-info">
        <h2><c:out value="${totalProducts != null ? totalProducts : 0}"/></h2>
        <p>Products</p>
      </div>
    </article>

    <article class="stat-card" title="Total number of customers" tabindex="0" aria-live="polite" aria-atomic="true">
      <i class='bx bx-user' aria-hidden="true"></i>
      <div class="stat-info">
        <h2><c:out value="${totalCustomers != null ? totalCustomers : 0}"/></h2>
        <p>Customers</p>
      </div>
    </article>

    <article class="stat-card" title="Number of bills created today" tabindex="0" aria-live="polite" aria-atomic="true">
      <i class='bx bx-receipt' aria-hidden="true"></i>
      <div class="stat-info">
        <h2><c:out value="${billsToday != null ? billsToday : 0}"/></h2>
        <p>Bills Today</p>
      </div>
    </article>
  </section>

  <nav class="dashboard-links" role="navigation" aria-label="Dashboard quick links">
  <a href="Product?action=list" class="dashboard-link" role="link"><i class='bx bx-box'></i> Manage Products</a>
  <a href="Customer?action=list" class="dashboard-link" role="link"><i class='bx bx-user'></i> Manage Customers</a>
   <a href="Staff?action=list" class="dashboard-link" role="link"><i class='bx bx-user'></i> Manage Staffs</a>
  <a href="Bill?action=create" class="dashboard-link" role="link"><i class='bx bx-receipt'></i> Add Bill</a>
  <a href="StaffDashboard?action=allTransactions" class="dashboard-link" role="link"><i class='bx bx-history'></i> Transaction History</a>
   
  <a href="${pageContext.request.contextPath}/AdminDashboard?action=auditLog" class="dashboard-link" role="link"><i class='bx bx-file-find'></i> Audit Log</a>
</nav>
<!-- Customer Growth Chart -->

<div style="display: flex; gap: 40px; flex-wrap: wrap; justify-content: center; align-items: flex-start;">

    <!-- Top Selling Products Chart -->
    <div style="background:white; padding:20px; border-radius:16px; flex: 1 1 500px; max-width:600px;">
        <h2>Top Selling Products</h2>
        <canvas id="topProductsChart" height="150"></canvas>
    </div>

    <!-- New Customers Chart -->
    <div style="background:white; padding:20px; border-radius:16px; flex: 1 1 500px; max-width:600px;">
        <h2>New Customers in Last 6 Months</h2>
        <canvas id="customerChart" height="150"></canvas>
    </div>

</div>

<script>
    // Top Products Chart
    fetch('Bill?action=topProductsData')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => item.name);
            const sold = data.map(item => item.sold);

            const backgroundColors = [
                'rgba(54, 162, 235, 0.7)',
                'rgba(255, 159, 64, 0.7)',
                'rgba(255, 99, 132, 0.7)',
                'rgba(153, 102, 255, 0.7)',
                'rgba(75, 192, 192, 0.7)'
            ];
            const borderColors = [
                'rgba(54, 162, 235, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(75, 192, 192, 1)'
            ];

            const bgColors = labels.map((_, i) => backgroundColors[i % backgroundColors.length]);
            const bdColors = labels.map((_, i) => borderColors[i % borderColors.length]);

            const ctx = document.getElementById('topProductsChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Units Sold',
                        data: sold,
                        backgroundColor: bgColors,
                        borderColor: bdColors,
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { display: true, position: 'top' },
                        title: { display: true, text: 'Top Selling Products' }
                    },
                    scales: {
                        y: { beginAtZero: true },
                        x: { title: { display:true, text:'Products' } }
                    }
                }
            });
        });

    // Customer Growth Chart
    fetch('Customer?action=monthlyChartData')
        .then(res => res.json())
        .then(data => {
            const labels = data.map(item => item.month);
            const counts = data.map(item => item.count);

            const ctx = document.getElementById('customerChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'New Customers',
                        data: counts,
                        fill: true,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        tension: 0.3,
                        pointRadius: 5,
                        pointBackgroundColor: 'rgba(75, 192, 192, 1)'
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { display: true, position: 'top' },
                        title: { display: true, text: 'Customer Growth (Last 6 Months)' }
                    },
                    scales: {
                        y: { beginAtZero: true, title: { display:true, text:'Customers' } },
                        x: { title: { display:true, text:'Month' } }
                    }
                }
            });
        })
        .catch(err => console.error('Error fetching chart data:', err));
</script>
  <h2 class="transactions-title">Last Transactions</h2>
  <div class="table-wrapper" role="region" aria-label="Last 5 Transactions" tabindex="0">
    <table>
      <thead>
        <tr>
          <th scope="col">Bill ID</th>
          <th scope="col">Staff id</th>
          <th scope="col">Date</th>
          <th scope="col">Customer</th>
          
          <th scope="col">Customer Phone</th>
          <th scope="col">Total Amount (Rs)</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="bill" items="${last5Bills}">
          <tr>
            <td>${bill.billId}</td>
            <td>${bill.staffId}</td>  <!-- Display Staff ID -->
            <td>${bill.billDate}</td>
            <td>${bill.customer.name}</td>
            <td>${bill.customer.phone}</td>
            <td>${bill.totalAmount}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty last5Bills}">
          <tr>
            <td colspan="5" style="text-align:center; padding: 30px 0;">No recent transactions found.</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <footer class="help-section" role="contentinfo">
    <p>Need help? Visit our <a href="help.html" target="_blank" rel="noopener noreferrer">Help & Support</a> page.</p>
  </footer>
</div>

</body>

</html>