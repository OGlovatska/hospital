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
    <div class="row gutters">
        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
            <div class="card h-100">
                <div class="card-body">
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <h6 class="mb-2 text-primary"><fmt:message key="hospitalisation.details"/></h6>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="hospitalisationDate"><fmt:message key="hospitalisation.date"/></label>
                                <input type="text" class="form-control" id="hospitalisationDate"
                                       placeholder="${requestScope.hospitalisation.startDate}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="dischargingDate"><fmt:message key="hospitalisation.discharging.date"/></label>
                                <input type="text" class="form-control" id="dischargingDate"
                                       placeholder="${requestScope.hospitalisation.endDate}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="status"><fmt:message key="common.status"/></label>
                                <input type="text" class="form-control" id="status"
                                       placeholder="${requestScope.hospitalisation.status}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="diagnosis"><fmt:message key="hospitalisation.diagnosis"/></label>
                                <input type="text" class="form-control" id="diagnosis"
                                       placeholder="${requestScope.hospitalisation.diagnosis}" readonly="readonly">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <nav style="padding-top: 15px">
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <button class="nav-link active" id="nav-appointments-tab" data-bs-toggle="tab"
                    data-bs-target="#nav-appointments"
                    type="button" role="tab" aria-controls="nav-patients" aria-selected="true">
                <fmt:message key="appointment.appointments"/>
            </button>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent" style="padding-top: 15px">
        <div class="tab-pane fade show active"
             id="nav-appointments" role="tabpanel" aria-labelledby="nav-appointments-tab">
            <div class="dataTables_wrapper dt-bootstrap5">
                <div class="d-flex">
                    <form class="row g-3" action="api" method="get">
                        <input type="hidden" name="command" value="hospitalisation"/>
                        <input type="hidden" name="hospitalisationId" value="${requestScope.hospitalisationId}"/>
                        <div class="col-auto">
                            <select name="limit" aria-controls="example" class="form-select form-select-sm"
                                    onchange=submit()>
                                <option value="10" ${requestScope.limit eq "10" ? "selected" : ""}>10</option>
                                <option value="25" ${requestScope.limit eq "25" ? "selected" : ""}>25</option>
                                <option value="50" ${requestScope.limit eq "50" ? "selected" : ""}>50</option>
                                <option value="100" ${requestScope.limit eq "100" ? "selected" : ""}>100</option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <select name="orderBy" aria-controls="example" class="form-select form-select-sm"
                                    onchange=submit()>
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
                            <select name="dir" aria-controls="example" class="form-select form-select-sm"
                                    onchange=submit()>
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
                        <th scope="col"><fmt:message key="common.date"/></th>
                        <th scope="col"><fmt:message key="common.staff"/></th>
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
                            <td><c:out value="${appointment.staffFirstName} ${appointment.staffLastName}"/></td>
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
                                                <a href="api?command=hospitalisation&hospitalisationId=${requestScope.hospitalisationId}&page=${requestScope.page - 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
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
                                                       href="api?command=hospitalisation&hospitalisationId=${requestScope.hospitalisationId}&page=${i}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}">${i}</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${requestScope.page < requestScope.numberOfPages}">
                                            <li class="paginate_button page-item next" id="example_next">
                                                <a href="api?command=hospitalisation&hospitalisationId=${requestScope.hospitalisationId}&page=${requestScope.page + 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
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
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>