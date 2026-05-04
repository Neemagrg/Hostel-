<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fee Tracking - SmartHostel</title>
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
            <a href="ManageFeesServlet" class="active">Fee Tracking</a>
            <a href="ManageComplaintsServlet">Complaints</a>
            <a href="ManageVisitorsServlet">Visitors</a>
            <div style="margin-top: auto;">
                <a href="LogoutServlet" style="background: #ef4444; color: white;">Logout</a>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Fee Tracking</h1>
            </div>

            <div class="card">
                <h3>Update Fee Record</h3>
                <form action="ManageFeesServlet" method="POST" style="display: grid; grid-template-columns: 1fr 1fr 1fr auto; gap: 1rem; align-items: end;">
                    <div class="form-group">
                        <label>Student</label>
                        <select name="userId" class="form-input" required>
                            <option value="">— Select —</option>
                            <c:forEach var="stu" items="${allStudents}">
                                <option value="${stu.userId}">${stu.userId} — ${stu.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Total Fee</label>
                        <input type="number" step="0.01" name="totalAmount" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label>Paid Amount</label>
                        <input type="number" step="0.01" name="paidAmount" class="form-input" required>
                    </div>
                    <button type="submit" class="btn" style="margin-bottom: 0.5rem;">Update</button>
                </form>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Fee Records</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Total</th>
                            <th>Paid</th>
                            <th>Due</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fee" items="${fees}">
                            <tr>
                                <td>${fee.userName}</td>
                                <td>$${fee.totalAmount}</td>
                                <td>$${fee.paidAmount}</td>
                                <td>$${fee.dueAmount}</td>
                                <td>
                                    <span class="status-badge status-${fee.status}">${fee.status}</span>
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
