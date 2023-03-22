<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateTime" uri="/WEB-INF/tld/dateTime.tld" %>
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
                            <td><c:out value="${dateTime:formatDateTime(appointment.dateTime)}"/></td>
                            <td><c:out value="${appointment.staffFirstName} ${appointment.staffLastName}"/></td>
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
                        api="api?command=hospitalisation&hospitalisationId=${requestScope.hospitalisationId}"
                        listSize="${requestScope.appointments.size()}"
                        locale="${sessionScope.lang}"
                />
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>