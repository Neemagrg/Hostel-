<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Visitors - SmartHostel</title>
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
            <a href="ManageComplaintsServlet">Complaints</a>
            <a href="ManageVisitorsServlet" class="active">Visitors</a>
            <div style="margin-top: auto;">
                <a href="LogoutServlet" style="background: #ef4444; color: white;">Logout</a>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Manage Visitors</h1>
            </div>

            <div class="card">
                <table>
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Visitor</th>
                            <th>Date</th>
                            <th>Purpose</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="visitor" items="${visitors}">
                            <tr>
                                <td>${visitor.studentName}</td>
                                <td>${visitor.visitorName}</td>
                                <td>${visitor.visitDate}</td>
                                <td>${visitor.purpose}</td>
                                <td>
                                    <span class="status-badge status-${visitor.status}">${visitor.status}</span>
                                </td>
                                <td>
                                    <c:if test="${visitor.status == 'pending'}">
                                        <div style="display: flex; gap: 0.5rem;">
                                            <form action="ManageVisitorsServlet" method="POST">
                                                <input type="hidden" name="visitorId" value="${visitor.visitorId}">
                                                <input type="hidden" name="status" value="approved">
                                                <button type="submit" class="btn" style="background: #10b981; width: auto; padding: 0.5rem 1rem;">Approve</button>
                                            </form>
                                            <form action="ManageVisitorsServlet" method="POST">
                                                <input type="hidden" name="visitorId" value="${visitor.visitorId}">
                                                <input type="hidden" name="status" value="rejected">
                                                <button type="submit" class="btn" style="background: #ef4444; width: auto; padding: 0.5rem 1rem;">Reject</button>
                                            </form>
                                        </div>
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
