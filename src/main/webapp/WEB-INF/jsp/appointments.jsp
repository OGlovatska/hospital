<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateTime" uri="/WEB-INF/tld/dateTime.tld" %>
<%@ taglib prefix="pgn" uri="/WEB-INF/tld/pagination.tld" %>
<%@ taglib prefix="filter" uri="/WEB-INF/tld/filter.tld" %>
<%@ taglib prefix="alert" tagdir="/WEB-INF/tags"%>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <alert:message sessionScope="${sessionScope}"/>

    <div class="dataTables_wrapper dt-bootstrap5">
        <div class="d-flex">
            <form class="row g-3" action="api" method="get">
                <input type="hidden" name="command" value="appointments-list"/>
                <filter:filter
                        nameLimit="limit"
                        selectedLimit="${requestScope.limit}"
                        nameOrderBy="orderBy"
                        selectedOrderBy="${requestScope.orderBy}"
                        optionsOrderBy="id, date_time, type"
                        nameDirection="dir"
                        selectedDirection="${requestScope.dir}"
                        locale="${sessionScope.lang}"
                />
            </form>
            <c:if test="${sessionScope.user.role eq 'DOCTOR' or sessionScope.user.role eq 'NURSE'}">
                <div class="col-auto" style="padding-left: 15px">
                    <form action="api" method="get">
                        <input type="hidden" name="command" value="create-appointment"/>
                        <button type="submit" class="btn btn-outline-primary me-2 btn-sm">
                            <fmt:message key="appointment.add"/>
                        </button>
                    </form>
                </div>
            </c:if>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="common.date"/></th>
                <th scope="col"><fmt:message key="patient.patient"/></th>
                <th scope="col"><fmt:message key="common.type"/></th>
                <th scope="col"><fmt:message key="appointment.description"/></th>
                <th scope="col"><fmt:message key="appointment.conclusion"/></th>
                <th scope="col"><fmt:message key="common.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="appointment" items="${requestScope.appointments}">
                <tr>
                    <td><c:out value="${appointment.id}"/></td>
                    <td><c:out value="${dateTime:formatDateTime(appointment.dateTime)}"/></td>
                    <td><c:out value="${appointment.patientFirstName} ${appointment.patientLastName}"/></td>
                    <td><c:out value="${appointment.type}"/></td>
                    <td><c:out value="${appointment.description}"/></td>
                    <td><c:out value="${appointment.conclusion}"/></td>
                    <td><c:out value="${appointment.status}"/></td>
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
                api="api?command=appointments-list&staffId=${requestScope.staffId}"
                listSize="${requestScope.appointments.size()}"
                locale="${sessionScope.lang}"
        />
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>