<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Room Application - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="room" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header">
                <div>
                    <p class="page-kicker">Rooms</p>
                    <h1 class="page-title">Apply for a room</h1>
                    <p class="page-subtitle">Choose an available room. Your request stays pending until the warden approves it.</p>
                </div>
            </header>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-error"><%= request.getParameter("error") %></div>
            <% } %>

            <section class="card card--elevated">
                <h3 class="card-title">Available rooms</h3>
                <p class="card-lead">Free beds are shown below. Submit one application at a time.</p>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Room No</th>
                                <th>Capacity</th>
                                <th>Free beds</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="room" items="${rooms}">
                                <tr>
                                    <td><strong>${room.roomNumber}</strong></td>
                                    <td>${room.capacity}</td>
                                    <td>${room.capacity - room.occupied}</td>
                                    <td>
                                        <form action="${ctx}/RoomApplicationServlet" method="POST" class="form-inline">
                                            <input type="hidden" name="roomId" value="${room.roomId}">
                                            <button type="submit" class="btn btn--inline">Apply</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </main>
    </div>
</body>
</html>
