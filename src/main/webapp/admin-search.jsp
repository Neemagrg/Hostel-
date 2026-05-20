<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search - SmartHostel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body style="display: block;">
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>SmartHostel</h2>
            <a href="AdminDashboardServlet">Dashboard</a>
            <a href="SearchServlet" class="active">Search &amp; Availability</a>
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
                <h1>Search &amp; room availability</h1>
            </div>

            <c:if test="${not empty searchError}">
                <div class="alert alert-error">${searchError}</div>
            </c:if>

            <div class="card">
                <h3>Find students</h3>
                <form action="SearchServlet" method="get" style="display: grid; gap: 1rem;">
                    <div class="form-group">
                        <label>By name or email</label>
                        <input type="text" name="name" class="form-input" value="${paramName}" placeholder="e.g. Priya">
                    </div>
                    <div class="form-group">
                        <label>By room number (allocated)</label>
                        <input type="text" name="roomNumber" class="form-input" value="${paramRoomNumber}" placeholder="e.g. 101">
                    </div>
                    <div class="form-group" style="display: flex; align-items: center; gap: 0.5rem;">
                        <input type="checkbox" name="listAvailable" value="1" id="listAvail" ${listAvailable == '1' || listAvailable == 'true' ? 'checked' : ''}>
                        <label for="listAvail" style="margin: 0;">Also list rooms with free beds</label>
                    </div>
                    <button type="submit" class="btn">Search</button>
                </form>
            </div>

            <c:if test="${not empty resultsByName}">
                <div class="card" style="margin-top: 1.5rem;">
                    <h3>Students matching name / email</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Course</th>
                                <th>Year</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${resultsByName}">
                                <tr>
                                    <td>${s.userId}</td>
                                    <td>${s.name}</td>
                                    <td>${s.email}</td>
                                    <td>${s.phone}</td>
                                    <td>${s.course}</td>
                                    <td>${s.yearOfStudy}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${not empty resultsByRoom}">
                <div class="card" style="margin-top: 1.5rem;">
                    <h3>Students by room</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Room</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${resultsByRoom}">
                                <tr>
                                    <td>${s.name}</td>
                                    <td>${s.email}</td>
                                    <td>${s.phone}</td>
                                    <td>${s.currentRoom}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${not empty availableRooms}">
                <div class="card" style="margin-top: 1.5rem;">
                    <h3>Rooms with availability</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Room</th>
                                <th>Capacity</th>
                                <th>Occupied</th>
                                <th>Free beds</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="room" items="${availableRooms}">
                                <tr>
                                    <td>${room.roomNumber}</td>
                                    <td>${room.capacity}</td>
                                    <td>${room.occupied}</td>
                                    <td>${room.capacity - room.occupied}</td>
                                    <td>${room.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
