<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<jsp:include page="fragments/head.jsp"/>
<body>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<jsp:include page="fragments/header.jsp"/>
<div class="jumbotron py-0">
    <div class="container">
        <c:if test="${sessionScope.user eq null}">
            <div class="pt-2">
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('admin@gmail.com', 'password')">
                    <fmt:message key="button.admin"/>
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('doctor@gmail.com', 'password')">
                    <fmt:message key="button.doctor"/>
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('nurse@gmail.com', 'password')">
                    <fmt:message key="button.nurse"/>
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('patient@gmail.com', 'password')">
                    <fmt:message key="button.patient"/>
                </button>
            </div>
        </c:if>
    </div>
</div>
<div class="container">
    <div class="lead py"><br>
        <h5><fmt:message key="about.descriptionTitle"/></h5>
        <fmt:message key="about.main"/>
        <h5> <fmt:message key="about.variant.title"/></h5>
        <fmt:message key="about.variant.text"/><br>
        <ul>
            <li><fmt:message key="about.sorting.patient"/>:
                <ul>
                    <li><fmt:message key="about.sorting.alphabetically"/>;</li>
                    <li><fmt:message key="about.sorting.dateOfBirth"/>;</li>
                </ul>
            </li>
            <li><fmt:message key="about.sorting.doctors"/>:
                <ul>
                    <li><fmt:message key="about.sorting.alphabetically"/>;</li>
                    <li><fmt:message key="about.sorting.category"/>;</li>
                    <li><fmt:message key="about.sorting.patients"/>.</li>
                </ul>
            </li>
        </ul>
        <fmt:message key="about.rights"/>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    function login(email, password) {
        setCredentials(email, password);
        $("#login_form").submit();
    }

    function setCredentials(email, password) {
        $('input[name="email"]').val(email);
        $('input[name="password"]').val(password);
    }
</script>
</body>
</html>
