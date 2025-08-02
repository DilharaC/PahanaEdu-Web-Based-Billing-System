<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Staff Dashboard</title>

<!-- Boxicons CDN for icons -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<style>
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: #e8f5e9;
    margin: 0; padding: 0;
  }
  header {
    background: #43a047;
    color: white;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .profile {
    display: flex;
    align-items: center;
    gap: 10px;
    position: relative;
    cursor: pointer;
  }
  .profile img {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    object-fit: cover;
  }
  .profile-name {
    font-weight: 600;
  }
  .dropdown-menu {
    position: absolute;
    top: 48px;
    right: 0;
    background: white;
    border-radius: 6px;
    box-shadow: 0 2px 8px rgb(0 0 0 / 0.15);
    display: none;
    min-width: 140px;
    z-index: 10;
  }
  .dropdown-menu a {
    display: block;
    padding: 10px 15px;
    color: #333;
    text-decoration: none;
  }
  .dropdown-menu a:hover {
    background-color: #f0f0f0;
  }
  .profile:hover .dropdown-menu {
    display: block;
  }

  .container {
    max-width: 1000px;
    margin: 30px auto;
    padding: 0 20px;
  }

  h1 {
    color: #2e7d32;
    margin-bottom: 25px;
  }

  /* Stats cards */
  .stats {
    display: flex;
    gap: 25px;
    margin-bottom: 40px;
  }
  .stat-card {
    background: white;
    flex: 1;
    padding: 20px 30px;
    border-radius: 12px;
    box-shadow: 0 6px 18px rgb(0 0 0 / 0.1);
    display: flex;
    align-items: center;
    gap: 20px;
  }
  .stat-card i {
    font-size: 48px;
    color: #43a047;
  }
  .stat-info h2 {
    margin: 0;
    font-size: 36px;
    color: #2e7d32;
  }
  .stat-info p {
    margin: 2px 0 0;
    font-weight: 600;
    color: #555;
  }

  /* Dashboard links */
  .dashboard-links {
    display: flex;
    gap: 30px;
  }
  .dashboard-link {
    background: #43a047;
    color: white;
    padding: 18px 25px;
    border-radius: 10px;
    font-weight: 700;
    font-size: 1.1rem;
    display: flex;
    align-items: center;
    gap: 12px;
    text-decoration: none;
    transition: background 0.3s ease;
  }
  .dashboard-link:hover {
    background: #357a38;
  }

  /* Help Section */
  .help-section {
    margin-top: 60px;
    background: white;
    padding: 20px 30px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 6px 18px rgb(0 0 0 / 0.1);
  }
  .help-section a {
    text-decoration: none;
    color: #43a047;
    font-weight: 600;
  }
  .help-section a:hover {
    text-decoration: underline;
  }

</style>

<script>
  function confirmLogout() {
    if(confirm("Are you sure you want to logout?")) {
      window.location.href = "Logout"; // change to your logout URL
    }
  }
</script>

</head>
<body>

<header>
  <div><h1>Staff Dashboard</h1></div>

  <div class="profile" title="Profile Menu">
    <img src="https://i.pravatar.cc/40" alt="Profile Picture" />
    <span class="profile-name">${sessionScope.staffName}</span>
    <i class='bx bx-chevron-down' style="color: white;"></i>

    <div class="dropdown-menu">
      <a href="Profile">Profile</a>
      <a href="ChangePassword">Change Password</a>
      <a href="javascript:void(0);" onclick="confirmLogout();">Logout <i class='bx bx-log-out'></i></a>
    </div>
  </div>
</header>

<div class="container">
  <h1>Welcome, <span style="color:#388e3c;">${sessionScope.staffName}</span>!</h1>

  <div class="stats">
    <div class="stat-card">
      <i class='bx bx-box'></i>
      <div class="stat-info">
        <h2>${totalProducts}</h2>
        <p>Products</p>
      </div>
    </div>

    <div class="stat-card">
      <i class='bx bx-user'></i>
      <div class="stat-info">
        <h2>${totalCustomers}</h2>
        <p>Customers</p>
      </div>
    </div>

    <div class="stat-card">
      <i class='bx bx-receipt'></i>
      <div class="stat-info">
        <h2>${billsToday}</h2>
        <p>Bills Today</p>
      </div>
    </div>
  </div>

  <div class="dashboard-links">
    <a href="Product?action=list" class="dashboard-link"><i class='bx bx-box'></i> Manage Products</a>
    <a href="Customer?action=list" class="dashboard-link"><i class='bx bx-user'></i> Manage Customers</a>
    <a href="Bill?action=list" class="dashboard-link"><i class='bx bx-receipt'></i> Add Bill</a>
  </div>

  <div class="help-section">
    <p>Need help? Visit our <a href="help.html" target="_blank">Help & Support</a> page.</p>
  </div>
</div>

</body>
</html>