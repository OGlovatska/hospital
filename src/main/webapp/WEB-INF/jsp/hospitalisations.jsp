<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="function" uri="/WEB-INF/tld/dateTime.tld" %>
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
