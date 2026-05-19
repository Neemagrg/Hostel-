<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My VisitorS - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="visitors" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header">
                <div>
                    <p class="page-kicker">Visitors</p>
                    <h1 class="page-title">Visitor requests</h1>
                    <p class="page-subtitle">Request approval for guests. Warden actions appear in the history table.</p>
                </div>
            </header>

            <section class="card card--elevated">
                <h3 class="card-title">New request</h3>
                <form action="${ctx}/VisitorRequestServlet" method="POST" class="visitor-form-grid">
                    <div class="form-group">
                        <label for="vname">Visitor name</label>
                        <input id="vname" type="text" name="visitorName" class="form-input" required placeholder="Full name">
                    </div>
                    <div class="form-group">
                        <label for="vdate">Visit date</label>
                        <input id="vdate" type="date" name="visitDate" class="form-input" required>
                    </div>
                    <div class="form-group visitor-form-span">
                        <label for="vpurpose">Purpose</label>
                        <input id="vpurpose" type="text" name="purpose" class="form-input" required placeholder="e.g. Family visit">
                    </div>
                    <div class="form-group visitor-form-actions">
                        <button type="submit" class="btn btn--narrow">Submit request</button>
                    </div>
                </form>
            </section>

            <section class="card card--elevated section-spaced">
                <h3 class="card-title">History</h3>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Visitor</th>
                                <th>Date</th>
                                <th>Purpose</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="visitor" items="${visitors}">
                                <tr>
                                    <td>${visitor.visitorName}</td>
                                    <td>${visitor.visitDate}</td>
                                    <td>${visitor.purpose}</td>
                                    <td>
                                        <span class="status-badge status-${visitor.status}">${visitor.status}</span>
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
