<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">

<head>
    <jsp:include page="fragments/head.jsp"/>
</head>
<body>
<div class="container mt-5">
<%--    <h1>Oops! Something went wrong.</h1>--%>
<%--    <p>We're sorry, but there seems to be an error.</p>--%>
<%--    <p>Please try again later.</p>--%>
    <h1><fmt:message key="exception.title"/></h1>
    <p><fmt:message key="exception.text"/></p>
    <div class="text-center mt-5">
        <a href="api" class="btn btn-primary">Home</a>
    </div>
</div>
</body>
</html>
