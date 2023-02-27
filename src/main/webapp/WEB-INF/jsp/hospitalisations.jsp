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
                        <option value="" selected disabled>Order by</option>
                        <option value="start_date" ${requestScope.orderBy eq "start_date" ? "selected" : ""}>
                            Hospitalisation date
                        </option>
                        <option value="end_date" ${requestScope.orderBy eq "end_date" ? "selected" : ""}>
                            Discharging date
                        </option>
                        <option value="status" ${requestScope.orderBy eq "status" ? "selected" : ""}>
                            Status
                        </option>
                    </select>
                </div>
                <div class="col-auto">
                    <select name="dir" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="" selected disabled>Direction</option>
                        <option value="ASC" ${requestScope.dir eq "ASC" ? "selected" : ""}>Ascending
                        </option>
                        <option value="DESC" ${requestScope.dir eq "DESC" ? "selected" : ""}>
                            Descending
                        </option>
                    </select>
                </div>
            </form>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Hospitalisation date</th>
                <th scope="col">Discharging date</th>
                <th scope="col">Diagnosis</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="hospitalisation" items="${requestScope.hospitalisations}">
                <tr onclick="document.location='api?command=hospitalisation&hospitalisationId=${hospitalisation.id}';" onmouseover=""
                    style="cursor: pointer;">
                    <td><c:out value="${hospitalisation.id}"/></td>
                    <td><c:out value="${hospitalisation.startDate}"/></td>
                    <td><c:out value="${hospitalisation.endDate}"/></td>
                    <td><c:out value="${hospitalisation.diagnosis}"/></td>
                    <td><c:out value="${hospitalisation.status}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

<%--        <div class="table-responsive">--%>
<%--            <table class="table accordion">--%>
<%--                <thead>--%>
<%--                <tr>--%>
<%--                    <th scope="col">#</th>--%>
<%--                    <th scope="col">Hospitalisation date</th>--%>
<%--                    <th scope="col">Discharging date</th>--%>
<%--                    <th scope="col">Diagnosis</th>--%>
<%--                    <th scope="col">Status</th>--%>
<%--                </tr>--%>
<%--                </thead>--%>
<%--                <tbody>--%>
<%--                <c:forEach var="hospitalisation" items="${requestScope.hospitalisations}">--%>
<%--                    <tr data-bs-toggle="collapse" data-bs-target="#r${hospitalisation.id}">--%>
<%--                        <th scope="row">${hospitalisation.id} <i class="bi bi-chevron-down"></i></th>--%>
<%--                        <td>${hospitalisation.startDate}</td>--%>
<%--                        <td>${hospitalisation.endDate}</td>--%>
<%--                        <td>${hospitalisation.diagnosis}</td>--%>
<%--                        <td>${hospitalisation.status}</td>--%>
<%--                    </tr>--%>
<%--                    <tr class="collapse accordion-collapse" id="r${hospitalisation.id}" data-bs-parent=".table">--%>
<%--                        <td colspan="8">--%>
<%--                            <div class="accordion-body">--%>
<%--                                <c:choose>--%>
<%--                                    <c:when test="${fn:length(hospitalisation.appointments) > 0}">--%>
<%--                                        <div style="display: flex; align-items: center; align-self: center; justify-content: center; justify-self: center; width: 80%; margin: 0 auto;">--%>
<%--                                            <h6>Appointments</h6>--%>
<%--                                        </div>--%>
<%--                                        <table class="table table-bordered">--%>
<%--                                            <thead>--%>
<%--                                            <tr>--%>
<%--                                                <th>#</th>--%>
<%--                                                <th>Date</th>--%>
<%--                                                <th>Staff</th>--%>
<%--                                                <th>Specialisation</th>--%>
<%--                                                <th>Type</th>--%>
<%--                                                <th>Description</th>--%>
<%--                                                <th>Conclusion</th>--%>
<%--                                                <th>Status</th>--%>
<%--                                            </tr>--%>
<%--                                            </thead>--%>
<%--                                            <tbody>--%>
<%--                                            <c:forEach var="appointment" items="${hospitalisation.appointments}">--%>
<%--                                                <tr>--%>
<%--                                                    <td>${appointment.id}</td>--%>
<%--                                                    <td>${function:formatDateTime(appointment.dateTime)}</td>--%>
<%--                                                    <td>${appointment.staffFirstName} ${appointment.staffLastName}</td>--%>
<%--                                                    <td>${appointment.specialisation}</td>--%>
<%--                                                    <td>${appointment.type}</td>--%>
<%--                                                    <td>${appointment.description}</td>--%>
<%--                                                    <td>${appointment.conclusion}</td>--%>
<%--                                                    <td>${appointment.status}</td>--%>
<%--                                                </tr>--%>
<%--                                            </c:forEach>--%>
<%--                                            </tbody>--%>
<%--                                        </table>--%>
<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <div style="display: flex; align-items: center; align-self: center; justify-content: center; justify-self: center; width: 80%; margin: 0 auto;">--%>
<%--                                            <h6>No appointments</h6>--%>
<%--                                        </div>--%>
<%--                                    </c:otherwise>--%>
<%--                                </c:choose>--%>
<%--                            </div>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--                </tbody>--%>
<%--            </table>--%>
<%--        </div>--%>
        <div class="row">
            <div class="col-sm-12 col-md-5">
                <div class="dataTables_info" id="example_info" role="status" aria-live="polite">
                    Showing ${requestScope.offset + 1} to
                    <c:choose>
                        <c:when test="${fn:length(requestScope.hospitalisations) < requestScope.limit}">
                            ${requestScope.totalCount}
                        </c:when>
                        <c:otherwise>
                            ${requestScope.offset + requestScope.limit}
                        </c:otherwise>
                    </c:choose>
                    of ${requestScope.totalCount} entries
                </div>
            </div>
            <div class="col-sm-12 col-md-7">
                <div class="dataTables_paginate paging_simple_numbers" id="example_paginate">
                    <ul class="pagination">
                        <c:if test="${requestScope.totalCount > requestScope.limit}">
                            <c:choose>
                                <c:when test="${requestScope.page > 1}">
                                    <li class="paginate_button page-item previous" id="example_previous">
                                        <a href="api?command=hospitalisations-list&page=${requestScope.page - 1}&limit=${requestScope.limit}
                                                        &orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
                                           aria-controls="example" data-dt-idx="previous" tabindex="0"
                                           class="page-link">Previous</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="paginate_button page-item previous disabled" id="example_previous">
                                        <a href="#" aria-controls="example" data-dt-idx="previous" tabindex="0"
                                           class="page-link">Previous</a>
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
                                               href="api?command=hospitalisations-list&page=${i}&limit=${requestScope.limit}
                                                        &orderBy=${requestScope.orderBy}&dir=${requestScope.dir}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${requestScope.page < requestScope.numberOfPages}">
                                    <li class="paginate_button page-item next" id="example_next">
                                        <a href="api?command=hospitalisations-list&page=${requestScope.page + 1}&limit=${requestScope.limit}
                                                        &orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
                                           aria-controls="example" data-dt-idx="next" tabindex="0"
                                           class="page-link">Next</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="paginate_button page-item next disabled" id="example_previous">
                                        <a href="#" aria-controls="example" data-dt-idx="next" tabindex="0"
                                           class="page-link">Next</a>
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
</body>
</html>
