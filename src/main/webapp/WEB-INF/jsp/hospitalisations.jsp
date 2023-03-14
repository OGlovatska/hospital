<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="function" uri="http://com.epam/functions" %>
<%@ taglib prefix="tg" uri="/WEB-INF/pagination.tld" %>
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
                <div class="col-auto">
                    <select name="limit" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="10" ${requestScope.limit eq "10" ? "selected" : ""}>10
                        </option>
                        <option value="25" ${requestScope.limit eq "25" ? "selected" : ""}>25
                        </option>
                        <option value="50" ${requestScope.limit eq "50" ? "selected" : ""}>50
                        </option>
                        <option value="100"  ${requestScope.limit eq "100" ? "selected" : ""}>100
                        </option>
                    </select>
                </div>
                <div class="col-auto">
                    <select name="orderBy" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="" selected disabled><fmt:message key="common.order.by"/></option>
                        <option value="id"  ${requestScope.orderBy eq "id" ? "selected" : ""}>
                            <fmt:message key="common.default"/>
                        </option>
                        <option value="start_date" ${requestScope.orderBy eq "start_date" ? "selected" : ""}>
                            <fmt:message key="hospitalisation.date"/>
                        </option>
                        <option value="end_date" ${requestScope.orderBy eq "end_date" ? "selected" : ""}>
                            <fmt:message key="hospitalisation.discharging.date"/>
                        </option>
                        <option value="status" ${requestScope.orderBy eq "status" ? "selected" : ""}>
                            <fmt:message key="common.status"/>
                        </option>
                    </select>
                </div>
                <div class="col-auto">
                    <select name="dir" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="" selected disabled><fmt:message key="common.direction"/></option>
                        <option value="ASC" ${requestScope.dir eq "ASC" ? "selected" : ""}>
                            <fmt:message key="common.ascending"/>
                        </option>
                        <option value="DESC" ${requestScope.dir eq "DESC" ? "selected" : ""}>
                            <fmt:message key="common.descending"/>
                        </option>
                    </select>
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
        <tg:pagination
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
