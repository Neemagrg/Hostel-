<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - SMART-HOSTEL</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body class="app-body" style="display: block;">
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>SmartHostel</h2>
            <a href="AdminDashboardServlet" class="active">Dashboard</a>
            <a href="SearchServlet">Search &amp; Availability</a>
            <a href="ManageStudentsServlet">Manage Students</a>
            <a href="ManageRoomsServlet">Manage Rooms</a>
            <a href="ManageFeesServlet">Fee Tracking</a>
            <a href="ManageComplaintsServlet">Complaints</a>
            <a href="ManageVisitorsServlet">Visitors</a>
            <div style="margin-top: auto;">
                <a href="LogoutServlet" style="background: #ef4444; color: white;">Logout</a>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Welcome, ${sessionScope.user.name}</h1>
                <div class="date"><%= new java.util.Date() %></div>
            </div>

            <c:if test="${not empty dashboardError}">
                <div class="alert alert-error">${dashboardError}</div>
            </c:if>

            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Total Students</h3>
                    <div class="value">${totalStudents}</div>
                </div>
                <div class="stat-card">
                    <h3>Rooms With Vacancy</h3>
                    <div class="value">${roomsWithVacancy}</div>
                </div>
                <div class="stat-card">
                    <h3>Free Beds (total)</h3>
                    <div class="value">${availableBeds}</div>
                </div>
                <div class="stat-card">
                    <h3>Total Room Units</h3>
                    <div class="value">${totalRooms}</div>
                </div>
                <div class="stat-card">
                    <h3>Pending Complaints</h3>
                    <div class="value" style="color: #ef4444;">${pendingComplaints}</div>
                </div>
                <div class="stat-card">
                    <h3>Resolved Complaints</h3>
                    <div class="value" style="color: #10b981;">${resolvedComplaints}</div>
                </div>
                <div class="stat-card">
                    <h3>Fees — Paid records</h3>
                    <div class="value">${feesPaidCount}</div>
                </div>
                <div class="stat-card">
                    <h3>Fees — Unpaid / Partial</h3>
                    <div class="value">${feesUnpaidCount}</div>
                </div>
                <div class="stat-card">
                    <h3>Total Outstanding Due</h3>
                    <div class="value" style="color: #f97316;">$${totalDue}</div>
                </div>
            </div>

            <div class="card">
                <h3>Reports snapshot</h3>
                <p>This dashboard summarizes occupancy, complaints, and fee collection. Use <strong>Search &amp; Availability</strong> to find students by name or room, and to list rooms that still have free beds.</p>
            </div>
        </div>
    </div>
</body>
</html>
