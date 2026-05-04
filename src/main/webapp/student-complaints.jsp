<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Complaints - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="complaints" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header">
                <div>
                    <p class="page-kicker">Support</p>
                    <h1 class="page-title">My complaints</h1>
                    <p class="page-subtitle">Report maintenance issues. Staff will update the status when resolved.</p>
                </div>
            </header>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-error"><%= request.getParameter("error") %></div>
            <% } %>

            <section class="card card--elevated">
                <h3 class="card-title">Submit a complaint</h3>
                <p class="card-lead">Pick a category and describe the problem clearly.</p>
                <form action="${ctx}/SubmitComplaintServlet" method="POST" class="stack-form">
                    <div class="form-group">
                        <label for="cat">Category</label>
                        <select id="cat" name="category" class="form-input" required>
                            <option value="room_issue">Room issue</option>
                            <option value="electricity">Electricity</option>
                            <option value="water">Water</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="desc">Description</label>
                        <textarea id="desc" name="description" class="form-input" rows="4" required placeholder="Describe what needs attention…"></textarea>
                    </div>
                    <button type="submit" class="btn btn--narrow">Submit complaint</button>
                </form>
            </section>

            <section class="card card--elevated section-spaced">
                <h3 class="card-title">History</h3>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th>Description</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="complaint" items="${complaints}">
                                <tr>
                                    <td>${complaint.category}</td>
                                    <td>${complaint.description}</td>
                                    <td>${complaint.createdAt}</td>
                                    <td>
                                        <span class="status-badge status-${complaint.status}">${complaint.status}</span>
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
