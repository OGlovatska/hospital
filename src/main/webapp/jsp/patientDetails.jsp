<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"
          rel="stylesheet">

    <script>
        $(document).ready(function () {
            $('.date').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true
            });

            $('.close-button').unbind();

            $('.close-button').click(function () {
                if ($('.datepicker').is(":visible")) {
                    $('.date').datepicker('hide');
                } else {
                    $('.date').datepicker('show');
                }
            });

            $('#staffModal').on('hidden.bs.modal', function () {
                $('#staffModal form')[0].reset();
            });

            $('#hospitalisationModal').on('hidden.bs.modal', function () {
                $('#hospitalisationModal form')[0].reset();
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row gutters">
        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
            <div class="card h-100">
                <div class="card-body">
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <h6 class="mb-2 text-primary">Personal Details</h6>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="firstName">First name</label>
                                <input type="text" class="form-control" id="firstName"
                                       placeholder="${requestScope.currentPatient.firstName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="lastName">Last name</label>
                                <input type="text" class="form-control" id="lastName"
                                       placeholder="${requestScope.currentPatient.lastName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="eMail">Email</label>
                                <input type="email" class="form-control" id="eMail"
                                       placeholder="${requestScope.currentPatient.email}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="gender">Gender</label>
                                <input type="text" class="form-control" id="gender"
                                       placeholder="${requestScope.currentPatient.gender}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="dateOfBirth">Date of birth</label>
                                <input type="url" class="form-control" id="dateOfBirth"
                                       placeholder="${requestScope.currentPatient.dateOfBirth}" readonly="readonly">
                            </div>
                        </div>
                        <c:if test="${requestScope.hospitalisation != null}">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="hospitalisationDate">Hospitalisation date</label>
                                    <input type="url" class="form-control" id="hospitalisationDate"
                                           placeholder="${requestScope.hospitalisation.startDate}" readonly="readonly">
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="dischargingDate">Discharging date</label>
                                    <input type="url" class="form-control" id="dischargingDate"
                                           placeholder="${requestScope.hospitalisation.endDate}" readonly="readonly">
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="diagnosis">Diagnosis</label>
                                    <input type="url" class="form-control" id="diagnosis"
                                           placeholder="${requestScope.hospitalisation.diagnosis}" readonly="readonly">
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${sessionScope.user.role eq 'DOCTOR' and requestScope.hospitalisation != null and requestScope.hospitalisation.endDate == null}">
                        <div style="padding-top: 15px">
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-danger me-2 btn-sm float-end"
                                        data-bs-toggle="modal"
                                        data-bs-target="#dischargeModal">
                                    Discharge
                                </button>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-primary me-2 btn-sm float-end"
                                        data-bs-toggle="modal"
                                        data-bs-target="#diagnosisModal">
                                    Determine diagnosis
                                </button>
                            </div>
                        </div>

                        <div class="modal fade" id="dischargeModal" tabindex="-1" aria-labelledby="daschargeModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="dischargeModalLabel">Discharge patient</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <form action="api" method="post">
                                        <input type="hidden" name="command" value="discharge-patient">
                                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                                        <input type="hidden" name="hospitalisationId" value="${requestScope.hospitalisation.id}">
                                        <div class="modal-body">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label class="form-label">End date</label>
                                                    <div class="input-group date insertInfo" data-provide="datepicker">
                                                        <input type="text" class="form-control" name="endDate" id="endDate">
                                                        <div class="input-group-addon close-button">
                                                            <span class="glyphicon glyphicon-th"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="status" class="form-label">Status</label>
                                                    <input type="text" name="status" value="DISCHARGED" id="discharged"
                                                           class="form-control" readonly="readonly" placeholder="DISCHARGED">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-outline-primary me-2">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="diagnosisModal" tabindex="-1" aria-labelledby="diagnosisModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="diagnosisModalLabel">Determine diagnosis</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <form action="api" method="post">
                                        <input type="hidden" name="command" value="determine-diagnosis">
                                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                                        <input type="hidden" name="hospitalisationId" value="${requestScope.hospitalisation.id}">
                                        <div class="modal-body">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label class="form-label">Diagnosis</label>
                                                    <textarea class="form-control" rows="3" name="diagnosis"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-outline-primary me-2">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'DOCTOR'}">
        <nav style="padding-top: 15px">
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                    <button class="nav-link <c:if test="${requestScope.activeTab == 'nav-staff'}">active</c:if>"
                            id="nav-staff-tab" data-bs-toggle="tab" data-bs-target="#nav-staff"
                            type="button" role="tab" aria-controls="nav-staff" aria-selected="true">Staff
                    </button>
                </c:if>
                <button class="nav-link <c:if test="${requestScope.activeTab == 'nav-hospitalisations'}">active</c:if>"
                        id="nav-hospitalisations-tab" data-bs-toggle="tab" data-bs-target="#nav-hospitalisations"
                        type="button" role="tab" aria-controls="nav-hospitalisations" aria-selected="false">
                    Hospitalisations
                </button>
            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent" style="padding-top: 15px">
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <div class="tab-pane fade <c:if test="${requestScope.activeTab == 'nav-staff'}">show active</c:if>"
                     id="nav-staff" role="tabpanel" aria-labelledby="nav-staff-tab">
                    <div class="dataTables_wrapper dt-bootstrap5">
                        <div class="d-flex">
                            <form class="row g-3" action="api" method="get">
                                <input type="hidden" name="command" value="patient"/>
                                <input type="hidden" name="patientId" value="${requestScope.patientId}"/>
                                <div class="col-auto">
                                    <select name="staffLimit" aria-controls="example" class="form-select form-select-sm">
                                        <option value="10" ${requestScope.staffLimit eq "10" ? "selected" : ""}>10</option>
                                        <option value="25" ${requestScope.staffLimit eq "25" ? "selected" : ""}>25</option>
                                        <option value="50" ${requestScope.staffLimit eq "50" ? "selected" : ""}>50</option>
                                        <option value="100"  ${requestScope.staffLimit eq "100" ? "selected" : ""}>100
                                        </option>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <select name="staffOrderBy" aria-controls="example" class="form-select form-select-sm">
                                        <option value="" disabled selected>Order by</option>
                                        <option value="first_name" ${requestScope.staffOrderBy eq "first_name" ? "selected" : ""}>
                                            First name
                                        </option>
                                        <option value="last_name" ${requestScope.staffOrderBy eq "last_name" ? "selected" : ""}>
                                            Last name
                                        </option>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <select name="staffDir" aria-controls="example" class="form-select form-select-sm">
                                        <option value="" disabled selected>Direction</option>
                                        <option value="ASC" ${requestScope.staffDir eq "ASC" ? "selected" : ""}>Ascending
                                        </option>
                                        <option value="DESC" ${requestScope.staffDir eq "DESC" ? "selected" : ""}>
                                            Descending
                                        </option>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <button type="submit" class="btn btn-outline-primary me-2 btn-sm">Show</button>
                                </div>
                            </form>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                                        data-bs-target="#staffModal">
                                    Assign staff
                                </button>
                            </div>
                        </div>
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">First name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Specialisation</th>
                                <th scope="col">Role</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="staff" items="${requestScope.assignedStaff}">
                                <tr>
                                    <td><c:out value="${staff.id}"/></td>
                                    <td><c:out value="${staff.firstName}"/></td>
                                    <td><c:out value="${staff.lastName}"/></td>
                                    <td><c:out value="${staff.email}"/></td>
                                    <td><c:out value="${staff.specialisation}"/></td>
                                    <td><c:out value="${staff.role}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="row">
                            <div class="col-sm-12 col-md-5">
                                <div class="dataTables_info" id="example_info" role="status" aria-live="polite">
                                    Showing
                                    <c:choose>
                                        <c:when test="${fn:length(requestScope.assignedStaff) eq 0}"> 0 entries</c:when>
                                        <c:otherwise> ${requestScope.offset + 1} to
                                            <c:choose>
                                                <c:when test="${fn:length(requestScope.assignedStaff) < requestScope.staffLimit}">
                                                    ${requestScope.staffCount}
                                                </c:when>
                                                <c:otherwise>
                                                    ${requestScope.staffOffset + requestScope.staffLimit}
                                                </c:otherwise>
                                            </c:choose>
                                            of ${requestScope.staffCount} entries
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-sm-12 col-md-7">
                                <div class="dataTables_paginate paging_simple_numbers" id="example_paginate">
                                    <ul class="pagination">
                                        <c:if test="${requestScope.staffCount > requestScope.staffLimit}">
                                            <c:choose>
                                                <c:when test="${requestScope.staffPage > 1}">
                                                    <li class="paginate_button page-item previous" id="example_previous">
                                                        <a href="api?command=patient&patientId=${requestScope.patientId}&staffPage=${requestScope.staffPage - 1}&staffLimit=${requestScope.staffLimit}&staffOrderBy=${requestScope.staffOrderBy}&staffDir=${requestScope.staffDir}"
                                                           aria-controls="example" data-dt-idx="previous" tabindex="0"
                                                           class="page-link">Previous</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="paginate_button page-item previous disabled"
                                                        id="example_previous">
                                                        <a href="#" aria-controls="example" data-dt-idx="previous"
                                                           tabindex="0"
                                                           class="page-link">Previous</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>

                                            <c:forEach begin="1" end="${requestScope.staffNumberOfPages}" var="i">
                                                <c:choose>
                                                    <c:when test="${requestScope.staffPage eq i}">
                                                        <li class="page-item active">
                                                            <a class="page-link">${i}</a>
                                                        </li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li class="page-item">
                                                            <a class="page-link"
                                                               href="api?command=patient&patientId=${requestScope.patientId}&staffPage=${i}&staffLimit=${requestScope.staffLimit}&staffOrderBy=${requestScope.staffOrderBy}&staffDir=${requestScope.staffDir}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${requestScope.staffPage < requestScope.staffNumberOfPages}">
                                                    <li class="paginate_button page-item next" id="example_next">
                                                        <a href="api?command=patient&patientId=${requestScope.patientId}&staffPage=${requestScope.staffPage + 1}&staffLimit=${requestScope.staffLimit}&staffOrderBy=${requestScope.staffOrderBy}&staffDir=${requestScope.staffDir}"
                                                           aria-controls="example" data-dt-idx="next" tabindex="0"
                                                           class="page-link">Next</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="paginate_button page-item next disabled"
                                                        id="example_previous">
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
            </c:if>
                <%--        <div class="tab-pane fade" id="nav-appointments" role="tabpanel" aria-labelledby="nav-appointments-tab">...</div>--%>
            <div class="tab-pane fade <c:if test="${requestScope.activeTab == 'nav-hospitalisations'}">show active</c:if>"
                 id="nav-hospitalisations" role="tabpanel" aria-labelledby="nav-hospitalisations-tab">
                <div id="example_wrapper" class="dataTables_wrapper dt-bootstrap5">
                    <div class="d-flex">
                        <form class="row g-3" action="api" method="get">
                            <input type="hidden" name="command" value="patient"/>
                            <input type="hidden" name="patientId" value="${requestScope.patientId}"/>
                            <div class="col-auto">
                                <select name="hospitalisationsLimit" aria-controls="example"
                                        class="form-select form-select-sm">
                                    <option value="10" ${requestScope.hospitalisationsLimit eq "10" ? "selected" : ""}>
                                        10
                                    </option>
                                    <option value="25" ${requestScope.hospitalisationsLimit eq "25" ? "selected" : ""}>
                                        25
                                    </option>
                                    <option value="50" ${requestScope.hospitalisationsLimit eq "50" ? "selected" : ""}>
                                        50
                                    </option>
                                    <option value="100"  ${requestScope.hospitalisationsLimit eq "100" ? "selected" : ""}>
                                        100
                                    </option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <select name="hospitalisationsOrderBy" aria-controls="example"
                                        class="form-select form-select-sm">
                                    <option selected value="id">Order by</option>
                                    <option value="start_date" ${requestScope.hospitalisationsOrderBy eq "start_date" ? "selected" : ""}>
                                        Start date
                                    </option>
                                    <option value="end_date" ${requestScope.hospitalisationsOrderBy eq "end_date" ? "selected" : ""}>
                                        End date
                                    </option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <select name="hospitalisationsDir" aria-controls="example"
                                        class="form-select form-select-sm">
                                    <option selected value="DESC">Direction</option>
                                    <option value="ASC" ${requestScope.hospitalisationsDir eq "ASC" ? "selected" : ""}>
                                        Ascending
                                    </option>
                                    <option value="DESC" ${requestScope.hospitalisationsDir eq "DESC" ? "selected" : ""}>
                                        Descending
                                    </option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-primary me-2 btn-sm">Show</button>
                            </div>
                        </form>
                        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                                        data-bs-target="#hospitalisationModal">
                                    Add hospitalisation
                                </button>
                            </div>
                        </c:if>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Start date</th>
                            <th scope="col">End date</th>
                            <th scope="col">Diagnosis</th>
                            <th scope="col">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="hospitalisation" items="${requestScope.hospitalisations}">
                            <tr>
                                <td><c:out value="${hospitalisation.id}"/></td>
                                <td><c:out value="${hospitalisation.startDate}"/></td>
                                <td><c:out value="${hospitalisation.endDate}"/></td>
                                <td><c:out value="${hospitalisation.diagnosis}"/></td>
                                <td><c:out value="${hospitalisation.status}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <div class="dataTables_info" role="status" aria-live="polite">
                                Showing
                                <c:choose>
                                    <c:when test="${fn:length(requestScope.hospitalisations) eq 0}"> 0 entries</c:when>
                                    <c:otherwise>
                                        ${requestScope.hospitalisationsOffset + 1} to
                                        <c:choose>
                                            <c:when test="${fn:length(requestScope.hospitalisations) < requestScope.hospitalisationsLimit}">
                                                ${requestScope.hospitalisationsCount}
                                            </c:when>
                                            <c:otherwise>
                                                ${requestScope.hospitalisationsOffset + requestScope.hospitalisationsLimit}
                                            </c:otherwise>
                                        </c:choose>
                                        of ${requestScope.hospitalisationsCount} entries
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-7">
                            <div class="dataTables_paginate paging_simple_numbers">
                                <ul class="pagination">
                                    <c:if test="${requestScope.hospitalisationsCount > requestScope.hospitalisationsLimit}">
                                        <c:choose>
                                            <c:when test="${requestScope.hospitalisationsPage > 1}">
                                                <li class="paginate_button page-item previous" id="example_previous">
                                                    <a href="api?command=patient&patientId=${requestScope.patientId}&hospitalisationsPage=${requestScope.hospitalisationsPage - 1}&hospitalisationsLimit=${requestScope.hospitalisationsLimit}&hospitalisationsOrderBy=${requestScope.hospitalisationsOrderBy}&hospitalisationsDir=${requestScope.hospitalisationsDir}"
                                                       aria-controls="example" data-dt-idx="previous" tabindex="0"
                                                       class="page-link">Previous</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="paginate_button page-item previous disabled"
                                                    id="example_previous">
                                                    <a href="#" aria-controls="example" data-dt-idx="previous"
                                                       tabindex="0"
                                                       class="page-link">Previous</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:forEach begin="1" end="${requestScope.hospitalisationsNumberOfPages}"
                                                   var="i">
                                            <c:choose>
                                                <c:when test="${requestScope.hospitalisationsPage eq i}">
                                                    <li class="page-item active">
                                                        <a class="page-link">${i}</a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item">
                                                        <a class="page-link"
                                                           href="api?command=patient&patientId=${requestScope.patientId}&hospitalisationsPage=${i}&hospitalisationsLimit=${requestScope.hospitalisationsLimit}&hospitalisationsOrderBy=${requestScope.hospitalisationsOrderBy}&hospitalisationsDir=${requestScope.hospitalisationsDir}">${i}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:choose>
                                            <c:when test="${requestScope.hospitalisationsPage < requestScope.hospitalisationsNumberOfPages}">
                                                <li class="paginate_button page-item next" id="example_next">
                                                    <a href="api?command=patient&patientId=${requestScope.patientId}&hospitalisationsPage=${requestScope.hospitalisationsPage + 1}&hospitalisationsLimit=${requestScope.hospitalisationsLimit}&hospitalisationsOrderBy=${requestScope.hospitalisationsOrderBy}&hospitalisationsDir=${requestScope.hospitalisationsDir}"
                                                       aria-controls="example" data-dt-idx="next" tabindex="0"
                                                       class="page-link">Next</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="paginate_button page-item next disabled"
                                                    id="example_previous">
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
        </div>

        <div class="modal fade" id="staffModal" tabindex="-1" aria-labelledby="staffModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staffModalLabel">Assign staff to patient</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="api" method="post" name="save-form">
                        <input type="hidden" name="command" value="assign-staff-to-patient">
                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Staff</label>
                                <select name="staffId" class="form-select form-select-md mb-3"
                                        aria-label=".form-select-md example">
                                    <option value="" disabled selected>Please choose</option>
                                    <c:forEach items="${requestScope.notAssignedStaff}" var="staff">
                                        <option name="staffId" value="${staff.id}">
                                                ${staff.firstName} ${staff.lastName}, ${staff.specialisation}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-outline-primary me-2">Assign</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="hospitalisationModal" tabindex="-1" aria-labelledby="hospitalisationModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="hospitalisationModalLabel">Add hospitalisation</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="api" method="post" name="save-form">
                        <input type="hidden" name="command" value="save-hospitalisation">
                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Start date</label>
                                <div class="input-group date insertInfo" data-provide="datepicker">
                                    <input type="text" class="form-control" name="startDate" id="startDate">
                                    <div class="input-group-addon close-button">
                                        <span class="glyphicon glyphicon-th"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <input type="text" name="status" value="HOSPITALIZED" id="status"
                                       class="form-control" readonly="readonly" placeholder="HOSPITALIZED">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-outline-primary me-2">Add</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>