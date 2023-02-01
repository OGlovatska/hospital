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
    <script>
        $(document).ready(function() {
            $('#patientModal').on('hidden.bs.modal', function () {
                $('#patientModal form')[0].reset();
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
                                <input type="text" class="form-control" id="firstName" placeholder="${requestScope.currentStaff.firstName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="lastName">Last name</label>
                                <input type="text" class="form-control" id="lastName" placeholder="${requestScope.currentStaff.lastName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="eMail">Email</label>
                                <input type="email" class="form-control" id="eMail" placeholder="${requestScope.currentStaff.email}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="role">Role</label>
                                <input type="text" class="form-control" id="role" placeholder="${requestScope.currentStaff.role}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="specialisation">Specialisation</label>
                                <input type="url" class="form-control" id="specialisation" placeholder="${requestScope.currentStaff.specialisation}" readonly="readonly">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <nav style="padding-top: 15px">
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <button class="nav-link <c:if test="${requestScope.activeTab == 'nav-patients'}">active</c:if>" id="nav-patients-tab" data-bs-toggle="tab" data-bs-target="#nav-patients"
                    type="button" role="tab" aria-controls="nav-patients" aria-selected="true">Patients
            </button>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent" style="padding-top: 15px">
        <div class="tab-pane fade <c:if test="${requestScope.activeTab == 'nav-patients'}">show active</c:if>"
             id="nav-patients" role="tabpanel" aria-labelledby="nav-patients-tab">
            <div class="dataTables_wrapper dt-bootstrap5">
                <div class="d-flex">
                    <form class="row g-3" action="api" method="get">
                        <input type="hidden" name="command" value="staff"/>
                        <input type="hidden" name="staffId" value="${requestScope.staffId}"/>
                        <input type="hidden" name="activeTab" value="nav-patients"/>
                        <div class="col-auto">
                            <select name="patientsLimit" aria-controls="example" class="form-select form-select-sm">
                                <option value="10" ${requestScope.patientsLimit eq "10" ? "selected" : ""}>10</option>
                                <option value="25" ${requestScope.patientsLimit eq "25" ? "selected" : ""}>25</option>
                                <option value="50" ${requestScope.patientsLimit eq "50" ? "selected" : ""}>50</option>
                                <option value="100"  ${requestScope.patientsLimit eq "100" ? "selected" : ""}>100
                                </option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <select name="patientsOrderBy" aria-controls="example" class="form-select form-select-sm">
                                <option selected value="id">Order by</option>
                                <option value="first_name" ${requestScope.patientsOrderBy eq "first_name" ? "selected" : ""}>
                                    First name
                                </option>
                                <option value="last_name" ${requestScope.patientsOrderBy eq "last_name" ? "selected" : ""}>
                                    Last name
                                </option>
                                <option value="date_of_birth" ${requestScope.patientsOrderBy eq "date_of_birth" ? "selected" : ""}>
                                    Date of birth
                                </option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <select name="patientsDir" aria-controls="example" class="form-select form-select-sm">
                                <option selected value="DESC">Direction</option>
                                <option value="ASC" ${requestScope.patientsDir eq "ASC" ? "selected" : ""}>Ascending
                                </option>
                                <option value="DESC" ${requestScope.patientsDir eq "DESC" ? "selected" : ""}>
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
                                data-bs-target="#patientModal">
                            Assign patient
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
                        <th scope="col">Date of birth</th>
                        <th scope="col">Gender</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="patient" items="${requestScope.assignedPatients}">
                        <tr>
                            <td><c:out value="${patient.id}"/></td>
                            <td><c:out value="${patient.firstName}"/></td>
                            <td><c:out value="${patient.lastName}"/></td>
                            <td><c:out value="${patient.email}"/></td>
                            <td><c:out value="${patient.dateOfBirth}"/></td>
                            <td><c:out value="${patient.gender}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-12 col-md-5">
                        <div class="dataTables_info" role="status" aria-live="polite">
                            Showing
                            <c:choose>
                                <c:when test="${fn:length(requestScope.assignedPatients) eq 0}"> 0 entries</c:when>
                                <c:otherwise>
                                    ${requestScope.patientsOffset + 1} to
                                    <c:choose>
                                        <c:when test="${fn:length(requestScope.assignedPatients) < requestScope.patientsLimit}">
                                            ${requestScope.patientsCount}
                                        </c:when>
                                        <c:otherwise>
                                            ${requestScope.patientsOffset + requestScope.patientsLimit}
                                        </c:otherwise>
                                    </c:choose>
                                    of ${requestScope.patientsCount} entries
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <ul class="pagination">
                                <c:if test="${requestScope.patientsCount > requestScope.patientsLimit}">
                                    <c:choose>
                                        <c:when test="${requestScope.patientsPage > 1}">
                                            <li class="paginate_button page-item previous" id="example_previous">
                                                <a href="api?command=staff&staffId=${requestScope.staffId}&patientsPage=${requestScope.patientsPage - 1}&patientsLimit=${requestScope.patientsLimit}&patientsOrderBy=${requestScope.patientsOrderBy}&patientsDir=${requestScope.patientsDir}"
                                                   aria-controls="example" data-dt-idx="previous" tabindex="0"
                                                   class="page-link">Previous</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="paginate_button page-item previous disabled"
                                                id="example_previous">
                                                <a href="#" aria-controls="example" data-dt-idx="previous" tabindex="0"
                                                   class="page-link">Previous</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:forEach begin="1" end="${requestScope.patientsNumberOfPages}" var="i">
                                        <c:choose>
                                            <c:when test="${requestScope.patientsPage eq i}">
                                                <li class="page-item active">
                                                    <a class="page-link">${i}</a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item">
                                                    <a class="page-link"
                                                       href="api?command=staff&staffId=${requestScope.staffId}&patientsPage=${i}&patientsLimit=${requestScope.patientsLimit}&orderBy=${requestScope.patientsOrderBy}&patientsDir=${requestScope.patientsDir}">${i}</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${requestScope.patientsPage < requestScope.patientsNumberOfPages}">
                                            <li class="paginate_button page-item next" id="example_next">
                                                <a href="api?command=staff&staffId=${requestScope.staffId}&patientsPage=${requestScope.patientsPage + 1}&patientsLimit=${requestScope.patientsLimit}&patientsOrderBy=${requestScope.patientsOrderBy}&patientsDir=${requestScope.patientsDir}"
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
    </div>
</div>

<div class="modal fade" id="patientModal" tabindex="-1" aria-labelledby="patientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5">Assign patient to staff</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="api" method="post" name="save-form">
                <input type="hidden" name="command" value="assign-patient-to-staff">
                <input type="hidden" name="staffId" value="${requestScope.staffId}">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Patient</label>
                        <select name="patientId" class="form-select form-select-md mb-3"
                                aria-label=".form-select-md example">
                            <option value="" disabled selected>Please choose</option>
                            <c:forEach items="${requestScope.notAssignedPatients}" var="patient">
                                <option name="patientId" value="${patient.id}">
                                        ${patient.firstName} ${patient.lastName}
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
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>