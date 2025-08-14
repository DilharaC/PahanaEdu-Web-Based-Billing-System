<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Staff Management</title>

<!-- Boxicons CDN -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<style>
/* Reuse the same CSS from listCustomer.jsp with minor tweaks if needed */
body { font-family: Arial, sans-serif; background-color: #f4fff4; margin:0; padding:0; color:#000; transition: background-color 0.3s ease, color 0.3s ease; }
.header { background-color:#66bb6a; color:white; padding:15px; text-align:center; position:relative; display:flex; justify-content:center; align-items:center; gap:8px; }
.header h1 { font-size:1.8rem; margin:0; flex-grow:1; text-align:center; display:flex; justify-content:center; align-items:center; gap:8px; }
.back-home-btn { position:absolute; right:15px; top:50%; transform:translateY(-50%); background-color:#4caf50; color:white; padding:8px 15px; text-decoration:none; border-radius:4px; display:inline-flex; align-items:center; gap:6px; font-weight:bold; font-size:0.9rem; transition: background-color 0.3s ease; }
.back-home-btn:hover { background-color:#3e8e41; }
.container { display:flex; gap:20px; padding:20px; align-items:flex-start; }
.sidebar { width:250px; background:white; padding:15px; border:1px solid #ccc; border-radius:5px; position:sticky; top:20px; height:95vh; box-sizing:border-box; color:#000; transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease; }
.form-control { width:100%; padding:8px; margin-bottom:10px; border-radius:4px; border:1px solid #ccc; box-sizing:border-box; transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease; }
.sidebar h3 { color:#388e3c; display:flex; align-items:center; gap:6px; }
.search-btn { background-color:#66bb6a; color:white; padding:10px; width:100%; border:none; border-radius:4px; cursor:pointer; display:flex; align-items:center; justify-content:center; gap:5px; font-weight:bold; transition: background-color 0.3s ease; }
.search-btn:hover { background-color:#57a05a; }
.content { flex:1; background:white; padding:15px; border:1px solid #ccc; border-radius:5px; color:#000; transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease; }
.content-header { display:flex; justify-content:space-between; margin-bottom:15px; align-items:center; }
.add-btn { background-color:#4caf50; color:white; padding:8px 15px; text-decoration:none; border-radius:4px; display:inline-flex; align-items:center; justify-content:center; font-weight:bold; gap:6px; min-width:120px; transition: background-color 0.3s ease; }
.add-btn:hover { background-color:#3e8e41; }
table { width:100%; border-collapse:collapse; color:#000; transition: color 0.3s ease; }
th { background-color:#a5d6a7; padding:10px; text-align:left; }
td { padding:8px; border-bottom:1px solid #ddd; }
tr:hover { background-color:#f1f8f1; }
.btn-edit { background-color:#42a5f5; color:white; padding:5px 10px; border:none; border-radius:4px; cursor:pointer; display:inline-flex; align-items:center; gap:4px; transition: background-color 0.3s ease; }
.btn-edit:hover { background-color:#1976d2; }
.btn-delete { background-color:#ef5350; color:white; padding:5px 10px; border:none; border-radius:4px; cursor:pointer; display:inline-flex; align-items:center; gap:4px; transition: background-color 0.3s ease; }
.btn-delete:hover { background-color:#c62828; }
/* Dark Mode same as before */
body.dark-mode { background-color:#121212; color:#ddd; }
body.dark-mode .header { background-color:#1b5e20; color:#c8e6c9; }
body.dark-mode .back-home-btn { background-color:#2e7d32; color:#c8e6c9; }
body.dark-mode .back-home-btn:hover { background-color:#1b5e20; }
body.dark-mode .sidebar, body.dark-mode .content { background-color:#1e1e1e; border-color:#333; color:#ccc; }
body.dark-mode input.form-control, body.dark-mode button.search-btn, body.dark-mode a.add-btn, body.dark-mode .btn-edit, body.dark-mode .btn-delete { background-color:#2e7d32; color:#c8e6c9; border:1px solid #3e8e41; }
body.dark-mode table th { background-color:#388e3c; color:#c8e6c9; }
body.dark-mode table td { border-bottom:1px solid #444; color:#ddd; background-color:#1e1e1e; }
body.dark-mode table tr:hover { background-color:#2e7d32; }
</style>
</head>
<body>

<div class="header">
    <h1><i class='bx bxs-user'></i> Staff Management</h1>

    <button id="themeToggleBtn" aria-label="Toggle dark mode" title="Toggle dark mode" style="position:absolute; right: 70px; top:50%; transform:translateY(-50%); background:none; border:none; cursor:pointer; color:white; font-size:1.4rem;">
      <i class='bx bx-moon'></i>
    </button>

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
    <!-- Sidebar -->
    <div class="sidebar">
        <h3><i class='bx bx-filter'></i> Search</h3>
        <form method="get" action="Staff">
            <input type="text" name="search" value="${param.search}" placeholder="Search by name or phone..." class="form-control" />
            <button type="submit" class="search-btn"><i class='bx bx-search'></i> Search</button>
        </form>
    </div>

    <!-- Content -->
    <div class="content">
        <div class="content-header">
            <h2><i class='bx bx-list-ul'></i> Staff List</h2>
            <a href="Staff?action=add" class="add-btn"><i class='bx bx-plus'></i> Add Staff</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th><i class='bx bx-hash'></i> ID</th>
                    <th><i class='bx bxs-user'></i> Name</th>
                    <th><i class='bx bx-envelope'></i> Email</th>
                    <th><i class='bx bx-phone'></i> Phone</th>
                    <th><i class='bx bx-briefcase'></i> Job Title</th>
                    <th><i class='bx bx-cog'></i> Role</th>
                    <th><i class='bx bx-check-circle'></i> Status</th>
                    <th><i class='bx bx-cog'></i> Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${staffList}">
                    <tr>
                        <td>${staff.staffId}</td>
                        <td>${staff.fullName}</td>
                        <td>${staff.email}</td>
                        <td>${staff.phone}</td>
                        <td>${staff.jobTitle}</td>
                        <td>${staff.role}</td>
                        <td>
                            <c:choose>
                                <c:when test="${staff.status == 'active'}">
                                    <i class='bx bx-check-circle' style="color:green;"></i> Active
                                </c:when>
                                <c:otherwise>
                                    <i class='bx bx-x-circle' style="color:red;"></i> Inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="Staff" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="edit" />
                                <input type="hidden" name="staffId" value="${staff.staffId}" />
                                <button type="submit" class="btn-edit"><i class='bx bx-edit'></i> Edit</button>
                            </form>
                            <form action="Staff" method="post" style="display:inline;" onsubmit="return confirm('Delete this staff?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="staffId" value="${staff.staffId}" />
                                <button type="submit" class="btn-delete"><i class='bx bx-trash'></i> Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty staffList}">
                    <tr>
                        <td colspan="8" style="text-align:center; font-style:italic; color:#888;">No staff found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', () => {
    const themeToggleBtn = document.getElementById('themeToggleBtn');
    const body = document.body;
    const themeIcon = themeToggleBtn.querySelector('i');

    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        body.classList.add('dark-mode');
        themeIcon.className = 'bx bx-sun';
    } else {
        themeIcon.className = 'bx bx-moon';
    }

    themeToggleBtn.addEventListener('click', () => {
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

</body>
</html>
