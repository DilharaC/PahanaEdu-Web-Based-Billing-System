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

  /* Profile Sidebar */
  .profile-sidebar {
    position: fixed;
    top: 0;
    right: -320px; /* hidden by default */
    width: 300px;
    height: 100%;
    background: white;
    box-shadow: -3px 0 10px rgba(0,0,0,0.2);
    padding: 25px 20px;
    box-sizing: border-box;
    transition: right 0.3s ease;
    z-index: 1000;
    overflow-y: auto;
  }
  .profile-sidebar.open {
    right: 0;
  }
  .profile-sidebar h2 {
    margin-top: 0;
    color: #388e3c;
  }
  .profile-sidebar p {
    font-size: 1rem;
    margin: 12px 0;
    color: #333;
  }
  .profile-sidebar .close-btn {
    background: none;
    border: none;
    font-size: 28px;
    font-weight: bold;
    position: absolute;
    top: 15px;     /* more space from top */
    right: 15px;   /* more space from right */
    cursor: pointer;
    color: #388e3c;
    padding: 5px;  /* increases clickable area */
    border-radius: 50%;
    transition: background 0.2s ease;
}

.profile-sidebar .close-btn:hover {
    background: rgba(0,0,0,0.05);
}
  .profile-sidebar .btn-link {
    display: inline-block;
    margin: 10px 0;
    color: #43a047;
    font-weight: 600;
    text-decoration: none;
    cursor: pointer;
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
	    if (confirm("Are you sure you want to logout?")) {
	      window.location.href = "Logout"; // change to your logout URL
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

	    // If click is outside sidebar and outside profile button → close sidebar
	    if (!sidebar.contains(event.target) && !profileBtn.contains(event.target)) {
	      sidebar.classList.remove("open");
	    }
	  });
</script>

</head>
<body>

<header>
  <div><h1>Staff Dashboard</h1></div>

  <div class="profile" title="Profile Menu" onclick="toggleProfileSidebar()" style="cursor:pointer;">
    <img src="https://i.pravatar.cc/40" alt="Profile Picture" />
    <span class="profile-name">${sessionScope.fullName}</span>
    <i class='bx bx-chevron-down' style="color: white;"></i>
  </div>
</header>

<!-- Profile Sidebar -->
<div id="profileSidebar" class="profile-sidebar">
  <button onclick="toggleProfileSidebar()" class="close-btn" title="Close Profile">&times;</button>
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

<div class="container">
  <h1>Welcome, <span style="color:#388e3c;">${sessionScope.fullName}</span>!</h1>

  <div class="stats">
    <div class="stat-card">
      <i class='bx bx-box'></i>
      <div class="stat-info">
        <h2><c:out value="${totalProducts != null ? totalProducts : 0}"/></h2>
        <p>Products</p>
      </div>
    </div>

    <div class="stat-card">
      <i class='bx bx-user'></i>
      <div class="stat-info">
        <h2><c:out value="${totalCustomers != null ? totalCustomers : 0}"/></h2>
        <p>Customers</p>
      </div>
    </div>

    <div class="stat-card">
      <i class='bx bx-receipt'></i>
      <div class="stat-info">
        <h2><c:out value="${billsToday != null ? billsToday : 0}"/></h2>
        <p>Bills Today</p>
      </div>
    </div>
  </div>

  <div class="dashboard-links">
    <a href="Product?action=list" class="dashboard-link"><i class='bx bx-box'></i> Manage Products</a>
    <a href="Customer?action=list" class="dashboard-link"><i class='bx bx-user'></i> Manage Customers</a>
  <a href="Bill?action=create" class="dashboard-link"><i class='bx bx-receipt'></i> Add Bill</a>
  </div>

  <div class="help-section">
    <p>Need help? Visit our <a href="help.html" target="_blank">Help & Support</a> page.</p>
  </div>
</div>

</body>
</html>
