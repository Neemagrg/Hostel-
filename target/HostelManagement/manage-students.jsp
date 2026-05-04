<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Students - SmartHostel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body style="display: block;">
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>SmartHostel</h2>
            <a href="AdminDashboardServlet">Dashboard</a>
            <a href="SearchServlet">Search &amp; Availability</a>
            <a href="ManageStudentsServlet" class="active">Manage Students</a>
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
                <h1>Manage Students</h1>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <div class="card">
                <h3>Search students</h3>
                <form action="ManageStudentsServlet" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap; align-items: end;">
                    <div class="form-group" style="flex: 1; min-width: 200px;">
                        <label>Name, email, or phone</label>
                        <input type="text" name="q" class="form-input" value="${searchQuery}" placeholder="Search...">
                    </div>
                    <button type="submit" class="btn">Search</button>
                    <a href="ManageStudentsServlet" class="btn" style="background: #64748b; text-decoration: none; display: inline-flex; align-items: center;">Clear</a>
                </form>
            </div>

            <div class="grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 1.5rem;">
                <div class="card">
                    <h3>Add student</h3>
                    <form action="ManageStudentsServlet" method="post">
                        <input type="hidden" name="action" value="add">
                        <div class="form-group">
                            <label>Full name</label>
                            <input type="text" name="name" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" name="phone" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label>Initial password</label>
                            <input type="password" name="password" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label>Course</label>
                            <input type="text" name="course" class="form-input" placeholder="e.g. B.Tech CSE">
                        </div>
                        <div class="form-group">
                            <label>Year</label>
                            <input type="text" name="yearOfStudy" class="form-input" placeholder="e.g. 2nd Year">
                        </div>
                        <button type="submit" class="btn">Add student</button>
                    </form>
                </div>

                <div class="card">
                    <h3>Edit student</h3>
                    <c:choose>
                        <c:when test="${not empty editStudent}">
                            <form action="ManageStudentsServlet" method="post">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="userId" value="${editStudent.userId}">
                                <div class="form-group">
                                    <label>Full name</label>
                                    <input type="text" name="name" class="form-input" value="${editStudent.name}" required>
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" class="form-input" value="${editStudent.email}" required>
                                </div>
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" name="phone" class="form-input" value="${editStudent.phone}" required>
                                </div>
                                <div class="form-group">
                                    <label>Course</label>
                                    <input type="text" name="course" class="form-input" value="${editStudent.course}">
                                </div>
                                <div class="form-group">
                                    <label>Year</label>
                                    <input type="text" name="yearOfStudy" class="form-input" value="${editStudent.yearOfStudy}">
                                </div>
                                <button type="submit" class="btn">Save changes</button>
                                <a href="ManageStudentsServlet?q=${searchQuery}" class="btn" style="background: #64748b; margin-left: 0.5rem; text-decoration: none;">Cancel</a>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <p style="color: #64748b;">Click <strong>Edit</strong> on a student in the table to update their record.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="card" style="margin-top: 2rem;">
                <h3>Student list</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Course</th>
                            <th>Year</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                               <td>${student.userId}</td>
                               <td>${student.name}</td>
                               <td>${student.email}</td>
                               <td>${student.phone}</td>
                               <td>${student.course}</td>
                               <td>${student.yearOfStudy}</td>
                               <td>
                                   <a href="ManageStudentsServlet?editUserId=${student.userId}&amp;q=${searchQuery}" class="btn" style="background: #2563eb; width: auto; padding: 0.5rem 1rem; text-decoration: none; display: inline-block;">Edit</a>
                                   <form action="ManageStudentsServlet" method="POST" style="display:inline;">
                                       <input type="hidden" name="userId" value="${student.userId}">
                                       <input type="hidden" name="action" value="delete">
                                       <button type="submit" class="btn" style="background: #ef4444; width: auto; padding: 0.5rem 1rem;" onclick="return confirm('Delete this student?');">Delete</button>
                                   </form>
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
