<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="application"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html lang="${sessionScope.lang}">
<body>
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-end py-3 mb-4 border-bottom">
        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <a href="jsp/login.jsp" class="navbar-brand d-flex me-auto">
                    <img src="resources/images/logo.png" alt="logo" width="142" height="32">
                </a>
            </c:when>
            <c:otherwise>
                <a href="jsp/main.jsp" class="navbar-brand d-flex me-auto">
                    <img src="resources/images/logo.png" alt="logo" width="142" height="32">
                </a>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <form class="row g-3 needs-validation" id="login_form" action="api" method="post" novalidate>
                    <input type="hidden" name="command" value="login">
                    <div class="col-auto">
                        <input type="email" class="form-control" placeholder="<fmt:message key="placeholder.email"/>" name="email" required>
                    </div>
                    <div class="col-auto">
                        <input type="password" class="form-control" placeholder="<fmt:message key="placeholder.password"/>" name="password" required>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-outline-primary me-2">
                            <fmt:message key="button.login"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <form class="row g-3" action="api" method="post">
                    <input type="hidden" name="command" value="logout">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-outline-primary me-2">
                            <fmt:message key="button.logout"/></button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>

        <form class="row g-3">
            <div class="col-auto">
                <button type="submit" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'en'}">EN</c:when>
                        <c:otherwise>UA</c:otherwise>
                    </c:choose>
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item"
                           href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">EN</a></li>
                    <li><a class="dropdown-item"
                           href="${requestScope['javax.servlet.forward.request_uri']}?lang=ua">UA</a></li>
                </ul>
            </div>
        </form>
    </header>
</div>
</body>
<script>
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
</html>


