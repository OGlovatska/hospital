<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgn" uri="/WEB-INF/tld/pagination.tld" %>
<%@ taglib prefix="filter" uri="/WEB-INF/tld/filter.tld" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<main>
<div class="container">
    <div id="example_wrapper" class="dataTables_wrapper dt-bootstrap5">
        <div class="d-flex">
            <form class="row g-3" action="api" method="get">
                <input type="hidden" name="command" value="hospitalisations-list"/>
                <filter:filter
                        nameLimit="limit"
                        selectedLimit="${requestScope.limit}"
                        nameOrderBy="orderBy"
                        selectedOrderBy="${requestScope.orderBy}"
                        optionsOrderBy="id, start_date, end_date, status"
                        nameDirection="dir"
                        selectedDirection="${requestScope.dir}"
                        locale="${sessionScope.lang}"
                />
            </form>
            <form class="row g-3" action="api" method="post">
                <input type="hidden" name="command" value="export-hospital-card">
                <div class="col-auto" style="padding-left: 15px">
                    <button type="submit" class="btn btn-outline-primary me-1 btn-sm">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"></path>
                            <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"></path>
                        </svg>
                        <fmt:message key="common.export"/>
                    </button>
                </div>
            </form>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="hospitalisation.date"/></th>
                <th scope="col"><fmt:message key="hospitalisation.discharging.date"/></th>
                <th scope="col"><fmt:message key="hospitalisation.diagnosis"/></th>
                <th scope="col"><fmt:message key="common.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="hospitalisation" items="${requestScope.hospitalisations}">
                <tr onclick="document.location='api?command=hospitalisation&hospitalisationId=${hospitalisation.id}';"
                    onmouseover="" style="cursor: pointer;">
                    <td><c:out value="${hospitalisation.id}"/></td>
                    <td><c:out value="${hospitalisation.startDate}"/></td>
                    <td><c:out value="${hospitalisation.endDate}"/></td>
                    <td><c:out value="${hospitalisation.diagnosis}"/></td>
                    <td><c:out value="${hospitalisation.status}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <pgn:pagination
                offsetValue="${requestScope.offset}"
                limitValue="${requestScope.limit}"
                orderByValue="${requestScope.orderBy}"
                dirValue="${requestScope.dir}"
                pageValue="${requestScope.page}"
                numberOfPagesValue="${requestScope.numberOfPages}"
                totalCountValue="${requestScope.totalCount}"
                api="api?command=hospitalisations-list"
                listSize="${requestScope.hospitalisations.size()}"
                locale="${sessionScope.lang}"
        />
    </div>
</div>
</main>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
