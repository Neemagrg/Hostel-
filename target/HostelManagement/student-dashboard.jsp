<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="dashboard" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header page-header--dashboard">
                <div>
                    <p class="page-kicker">Overview</p>
                    <h1 class="page-title">Hello, <span class="display-name">${sessionScope.user.name}</span></h1>
                    <p class="page-subtitle">Here is a quick snapshot of your hostel account.</p>
                </div>
                <div class="page-header-meta">
                    <jsp:useBean id="now" class="java.util.Date" scope="page"/>
                    <time class="dashboard-time" datetime="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd'T'HH:mm:ssXXX"/>">
                        <fmt:formatDate value="${now}" pattern="EEE, MMM d, yyyy"/>
                        <span class="dashboard-time-sep">·</span>
                        <fmt:formatDate value="${now}" pattern="h:mm a"/>
                    </time>
                </div>
            </header>

            <c:if test="${not empty flashMsg}">
                <div class="alert alert-success">${flashMsg}</div>
            </c:if>

            <div class="stats-grid stats-grid--dash">
                <article class="stat-card stat-card--accent">
                    <h3>Allocated room</h3>
                    <p class="stat-card__value">${roomNumber}</p>
                </article>
                <article class="stat-card">
                    <h3>Fee status</h3>
                    <p class="stat-card__value stat-card__value--muted">${feeStatus}</p>
                </article>
                <article class="stat-card">
                    <h3>Due amount</h3>
                    <c:choose>
                        <c:when test="${dueAmount gt 0}">
                            <p class="stat-card__value stat-card__value--danger">
                                <fmt:formatNumber value="${dueAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p class="stat-card__value stat-card__value--neutral">
                                <fmt:formatNumber value="${dueAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                            </p>
                        </c:otherwise>
                    </c:choose>
                </article>
            </div>

            <section class="card card--elevated welcome-card">
                <h3 class="card-title">Hostel notifications</h3>
                <p class="card-lead" style="margin-bottom:0;">Welcome to SmartHostel. Keep your contact details up to date so the office can reach you about room changes or fees.</p>
            </section>
        </main>
    </div>
</body>
</html>
