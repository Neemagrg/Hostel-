<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Complaints - SmartHostel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body style="display: block;">
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>SmartHostel</h2>
            <a href="AdminDashboardServlet">Dashboard</a>
            <a href="SearchServlet">Search &amp; Availability</a>
            <a href="ManageStudentsServlet">Manage Students</a>
            <a href="ManageRoomsServlet">Manage Rooms</a>
            <a href="ManageFeesServlet">Fee Tracking</a>
            <a href="ManageComplaintsServlet" class="active">Complaints</a>
            <a href="ManageVisitorsServlet">Visitors</a>
            <div style="margin-top: auto;">
                <a href="LogoutServlet" style="background: #ef4444; color: white;">Logout</a>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Manage Complaints</h1>
            </div>

            <div class="card">
                <table>
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Category</th>
                            <th>Description</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="complaint" items="${complaints}">
                            <tr>
                                <td>${complaint.userName}</td>
                                <td>${complaint.category}</td>
                                <td>${complaint.description}</td>
                                <td>${complaint.createdAt}</td>
                                <td>
                                    <span class="status-badge status-${complaint.status}">${complaint.status}</span>
                                </td>
                                <td>
                                    <c:if test="${complaint.status == 'pending'}">
                                        <form action="ManageComplaintsServlet" method="POST">
                                            <input type="hidden" name="complaintId" value="${complaint.complaintId}">
                                            <input type="hidden" name="status" value="resolved">
                                            <button type="submit" class="btn" style="background: #10b981; width: auto; padding: 0.5rem 1rem;">Resolve</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
