<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="function" uri="http://com.epam/functions" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <div class="dataTables_wrapper dt-bootstrap5">
        <div class="d-flex">
            <form class="row g-3" action="api" method="get">
                <input type="hidden" name="command" value="appointments-list"/>
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
                        <option value="date_time" ${requestScope.orderBy eq "date_time" ? "selected" : ""}>
                            <fmt:message key="common.date"/>
                        </option>
                        <option value="type" ${requestScope.orderBy eq "type" ? "selected" : ""}>
                            <fmt:message key="common.type"/>
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
                    <td><c:out value="${function:formatDateTime(appointment.dateTime)}"/></td>
                    <td><c:out value="${appointment.patientFirstName} ${appointment.patientLastName}"/></td>
                    <td><c:out value="${appointment.type}"/></td>
                    <td><c:out value="${appointment.description}"/></td>
                    <td><c:out value="${appointment.conclusion}"/></td>
                    <td><c:out value="${appointment.status}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <div class="col-sm-12 col-md-5">
                <div class="dataTables_info" role="status" aria-live="polite">
                    <fmt:message key="pagination.showing"/>
                    <c:choose>
                        <c:when test="${fn:length(requestScope.appointments) eq 0}"> 0 <fmt:message key="pagination.entries"/></c:when>
                        <c:otherwise>
                            <fmt:message key="pagination.from"/> ${requestScope.offset + 1} <fmt:message key="pagination.to"/>
                            <c:choose>
                                <c:when test="${fn:length(requestScope.appointments) < requestScope.limit}">
                                    ${requestScope.totalCount}
                                </c:when>
                                <c:otherwise>
                                    ${requestScope.offset + requestScope.limit}
                                </c:otherwise>
                            </c:choose>
                            <fmt:message key="pagination.of"/> ${requestScope.totalCount}
                            <fmt:message key="pagination.entries"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-sm-12 col-md-7">
                <div class="dataTables_paginate paging_simple_numbers">
                    <ul class="pagination">
                        <c:if test="${requestScope.totalCount > requestScope.limit}">
                            <c:choose>
                                <c:when test="${requestScope.page > 1}">
                                    <li class="paginate_button page-item previous" id="example_previous">
                                        <a href="api?command=appointments-list&staffId=${requestScope.staffId}&page=${requestScope.page - 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
                                           aria-controls="example" data-dt-idx="previous" tabindex="0"
                                           class="page-link"><fmt:message key="pagination.previous"/></a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="paginate_button page-item previous disabled"
                                        id="example_previous">
                                        <a href="#" aria-controls="example" data-dt-idx="previous" tabindex="0"
                                           class="page-link"><fmt:message key="pagination.previous"/></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                <c:choose>
                                    <c:when test="${requestScope.page eq i}">
                                        <li class="page-item active">
                                            <a class="page-link">${i}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="api?command=appointments-list&staffId=${requestScope.staffId}&page=${i}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${requestScope.page < requestScope.numberOfPages}">
                                    <li class="paginate_button page-item next" id="example_next">
                                        <a href="api?command=appointments-list&staffId=${requestScope.staffId}&page=${requestScope.page + 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
                                           aria-controls="example" data-dt-idx="next" tabindex="0"
                                           class="page-link"><fmt:message key="pagination.next"/></a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="paginate_button page-item next disabled" id="example_previous">
                                        <a href="#" aria-controls="example" data-dt-idx="next" tabindex="0"
                                           class="page-link"><fmt:message key="pagination.next"/></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>