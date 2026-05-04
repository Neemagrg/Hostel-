<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - SmartHostel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body class="auth-body">
    <div class="auth-container">
        <div class="auth-header">
            <h1>SmartHostel</h1>
            <p>Welcome back! Please log in to continue.</p>
        </div>

        <% if (request.getParameter("success") != null) { %>
            <div class="alert" style="background: #d1fae5; color: #065f46; border: 1px solid #6ee7b7;">
                <%= request.getParameter("success") %>
            </div>
        <% } %>
        <% if (request.getParameter("msg") != null) { %>
            <div class="alert" style="background: #e0e7ff; color: #3730a3;">
                <%= request.getParameter("msg") %>
            </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" class="form-input" placeholder="admin@hostel.com" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-input" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn">Sign In</button>
        </form>

        <div class="auth-footer">
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
    </div>
</body>
</html>
