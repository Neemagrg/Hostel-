<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fee Status - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="fees" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header">
                <div>
                    <p class="page-kicker">Billing</p>
                    <h1 class="page-title">Fee status</h1>
                    <p class="page-subtitle">See what you owe and what you have already paid.</p>
                </div>
            </header>

            <c:choose>
                <c:when test="${not empty fee}">
                    <div class="fee-hero card card--elevated">
                        <p class="fee-hero-label">Amount due</p>
                        <c:choose>
                            <c:when test="${fee.dueAmount gt 0}">
                                <p class="fee-hero-amount fee-hero-amount--due">
                                    <fmt:formatNumber value="${fee.dueAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                                </p>
                            </c:when>
                            <c:otherwise>
                                <p class="fee-hero-amount fee-hero-amount--clear">
                                    <fmt:formatNumber value="${fee.dueAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                                </p>
                            </c:otherwise>
                        </c:choose>
                        <span class="status-badge status-${fee.status}">Status: ${fee.status}</span>
                    </div>

                    <div class="stats-grid stats-grid--two">
                        <article class="stat-card">
                            <h3>Total fee</h3>
                            <p class="stat-card__value">
                                <fmt:formatNumber value="${fee.totalAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                            </p>
                        </article>
                        <article class="stat-card stat-card--paid">
                            <h3>Amount paid</h3>
                            <p class="stat-card__value stat-card__value--success">
                                <fmt:formatNumber value="${fee.paidAmount}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                            </p>
                        </article>
                    </div>
                </c:when>
                <c:otherwise>
                    <section class="card card--elevated empty-state">
                        <h3 class="card-title">No fee record yet</h3>
                        <p class="card-lead" style="margin:0;">The office has not entered fee details for your account. Check again later or contact the hostel desk.</p>
                    </section>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</body>
</html>
