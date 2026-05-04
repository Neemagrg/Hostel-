<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Rooms - SmartHostel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body style="display: block;">
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>SmartHostel</h2>
            <a href="AdminDashboardServlet">Dashboard</a>
            <a href="SearchServlet">Search &amp; Availability</a>
            <a href="ManageStudentsServlet">Manage Students</a>
            <a href="ManageRoomsServlet" class="active">Manage Rooms</a>
            <a href="ManageFeesServlet">Fee Tracking</a>
            <a href="ManageComplaintsServlet">Complaints</a>
            <a href="ManageVisitorsServlet">Visitors</a>
            <div style="margin-top: auto;">
                <a href="LogoutServlet" style="background: #ef4444; color: white;">Logout</a>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Manage Rooms</h1>
            </div>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error"><%= request.getAttribute("error") %></div>
            <% } else if (request.getParameter("error") != null) { %>
                <div class="alert alert-error"><%= request.getParameter("error") %></div>
            <% } %>

            <div class="grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem;">
                <div class="card">
                    <h3>Add New Room</h3>
                    <form action="ManageRoomsServlet" method="POST">
                        <input type="hidden" name="action" value="add">
                        <div class="form-group">
                            <label>Room Number</label>
                            <input type="text" name="roomNumber" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label>Capacity</label>
                            <input type="number" name="capacity" class="form-input" required>
                        </div>
                        <button type="submit" class="btn">Add Room</button>
                    </form>
                </div>

                <div class="card">
                    <h3>Allocate Student</h3>
                    <form action="ManageRoomsServlet" method="POST">
                        <input type="hidden" name="action" value="allocate">
                        <div class="form-group">
                            <label>Select Room</label>
                            <select name="roomId" class="form-input" required>
                                <c:forEach var="room" items="${rooms}">
                                    <c:if test="${room.occupied < room.capacity}">
                                        <option value="${room.roomId}">${room.roomNumber} (Available: ${room.capacity - room.occupied})</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Select Student</label>
                            <select name="userId" class="form-input" required>
                                <c:forEach var="student" items="${students}">
                                    <option value="${student.userId}">${student.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn" style="background: #10b981;">Allocate</button>
                    </form>
                </div>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Pending Room Applications</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Requested Room</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="req" items="${requests}">
                            <tr>
                                <td>${req.userName}</td>
                                <td>${req.roomNumber}</td>
                                <td>${req.allocationDate}</td>
                                <td>
                                    <div style="display: flex; gap: 0.5rem;">
                                        <form action="ManageRoomsServlet" method="POST">
                                            <input type="hidden" name="allocId" value="${req.allocationId}">
                                            <input type="hidden" name="action" value="approve">
                                            <button type="submit" class="btn" style="background: #10b981; width: auto; padding: 0.5rem 1rem;">Approve</button>
                                        </form>
                                        <form action="ManageRoomsServlet" method="POST">
                                            <input type="hidden" name="allocId" value="${req.allocationId}">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" class="btn" style="background: #ef4444; width: auto; padding: 0.5rem 1rem;">Reject</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Room List</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Room No</th>
                            <th>Capacity</th>
                            <th>Occupied</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td>${room.roomNumber}</td>
                                <td>${room.capacity}</td>
                                <td>${room.occupied}</td>
                                <td>
                                    <span class="status-badge ${room.occupied < room.capacity ? 'status-active' : 'status-unpaid'}">
                                        ${room.occupied < room.capacity ? 'Available' : 'Full'}
                                    </span>
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
