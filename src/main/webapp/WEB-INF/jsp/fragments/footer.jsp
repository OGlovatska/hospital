<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="application"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html lang="${sessionScope.lang}">
<div class="container">
    <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
        <p class="col-md-4 mb-0 text-muted">&copy; 2023 Hospital, Inc</p>

        <ul class="nav col-md-4 justify-content-end">
            <li class="nav-item"><a href="api?command=about" class="nav-link px-2 text-muted"><fmt:message key="button.about"/></a></li>
        </ul>
    </footer>
</div>
</html>

