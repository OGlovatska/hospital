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
    <h1><fmt:message key="accessDenied.title"/></h1>
    <p><fmt:message key="accessDenied.text"/></p>
    <div class="text-center mt-5">
        <a href="api" class="btn btn-primary"><fmt:message key="button.home"/></a>
    </div>
</div>
</body>
</html>