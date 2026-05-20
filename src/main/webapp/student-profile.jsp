<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - SmartHostel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body class="app-body">
    <c:set var="studentNavActive" value="profile" scope="request"/>
    <div class="dashboard-container">
        <%@ include file="WEB-INF/jspf/student-sidebar.jspf" %>

        <main class="main-content">
            <header class="page-header">
                <div>
                    <p class="page-kicker">Account</p>
                    <h1 class="page-title">My profile</h1>
                    <p class="page-subtitle">Your hostel registration details and room allocation.</p>
                </div>
            </header>

            <c:if test="${not empty warn}">
                <div class="alert alert-warn" role="status">${warn}</div>
            </c:if>

            <section class="profile-layout">
                <div class="profile-hero card card--elevated">
                    <div class="profile-avatar" aria-hidden="true">
                        <c:choose>
                            <c:when test="${empty profile.name}">?</c:when>
                            <c:otherwise>${fn:toUpperCase(fn:substring(profile.name, 0, 1))}</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="profile-hero-text">
                        <h2 class="profile-name display-name">${profile.name}</h2>
                        <p class="profile-meta">${profile.email}</p>
                        <c:if test="${profileFromDb == false}">
                            <span class="badge badge-muted">Session snapshot</span>
                        </c:if>
                    </div>
                </div>

                <div class="card card--elevated profile-details-card">
                    <h3 class="card-title">Your details</h3>
                    <p class="card-lead">Contact your hostel office to correct official records.</p>
                    <dl class="profile-dl">
                        <div class="profile-row">
                            <dt>Full name</dt>
                            <dd class="display-name">${profile.name}</dd>
                        </div>
                        <div class="profile-row">
                            <dt>Email</dt>
                            <dd><a class="inline-link" href="mailto:${profile.email}">${profile.email}</a></dd>
                        </div>
                        <div class="profile-row">
                            <dt>Phone</dt>
                            <dd><c:out value="${empty profile.phone ? '—' : profile.phone}" /></dd>
                        </div>
                        <div class="profile-row">
                            <dt>Course</dt>
                            <dd><c:out value="${empty profile.course ? '—' : profile.course}" /></dd>
                        </div>
                        <div class="profile-row">
                            <dt>Year of study</dt>
                            <dd><c:out value="${empty profile.yearOfStudy ? '—' : profile.yearOfStudy}" /></dd>
                        </div>
                        <div class="profile-row profile-row--highlight">
                            <dt>Allocated room</dt>
                            <dd><span class="room-pill">${allocatedRoom}</span></dd>
                        </div>
                    </dl>
                </div>
            </section>
        </main>
    </div>
</body>
</html>
