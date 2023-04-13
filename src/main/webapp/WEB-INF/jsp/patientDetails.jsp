<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgn" uri="/WEB-INF/tld/pagination.tld" %>
<%@ taglib prefix="filter" uri="/WEB-INF/tld/filter.tld" %>
<%@ taglib prefix="alert" tagdir="/WEB-INF/tags"%>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment-with-locales.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"
          rel="stylesheet">
    <script type="text/javascript" src="resources/js/common.js" defer></script>

    <script>
        $(document).ready(function () {
            customizeDatePicker();
            validateForm();
            clearModal();
        });
    </script>
</head>
<body>
<div class="container">
    <alert:message sessionScope="${sessionScope}"/>
    
    <div class="row gutters">
        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
            <div class="card h-100">
                <div class="card-body">
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <h6 class="mb-2 text-primary"><fmt:message key="common.personal.details"/></h6>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="firstName"><fmt:message key="common.first.name"/></label>
                                <input type="text" class="form-control" id="firstName"
                                       placeholder="${requestScope.currentPatient.firstName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="lastName"><fmt:message key="common.last.name"/></label>
                                <input type="text" class="form-control" id="lastName"
                                       placeholder="${requestScope.currentPatient.lastName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="eMail"><fmt:message key="common.email"/></label>
                                <input type="email" class="form-control" id="eMail"
                                       placeholder="${requestScope.currentPatient.email}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="gender"><fmt:message key="patient.gender"/></label>
                                <input type="text" class="form-control" id="gender"
                                       placeholder="${requestScope.currentPatient.gender}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="dateOfBirth"><fmt:message key="patient.date.of.birth"/></label>
                                <input type="url" class="form-control" id="dateOfBirth"
                                       placeholder="${requestScope.currentPatient.dateOfBirth}" readonly="readonly">
                            </div>
                        </div>
                        <c:if test="${requestScope.hospitalisation != null}">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="hospitalisationDate"><fmt:message key="hospitalisation.date"/></label>
                                    <input type="url" class="form-control" id="hospitalisationDate"
                                           placeholder="${requestScope.hospitalisation.startDate}" readonly="readonly">
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="dischargingDate"><fmt:message key="hospitalisation.discharging.date"/></label>
                                    <input type="url" class="form-control" id="dischargingDate"
                                           placeholder="${requestScope.hospitalisation.endDate}" readonly="readonly">
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="form-group">
                                    <label for="diagnosis"><fmt:message key="hospitalisation.diagnosis"/></label>
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
                                    <fmt:message key="patient.discharge"/>
                                </button>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-outline-primary me-2 btn-sm float-end"
                                        data-bs-toggle="modal"
                                        data-bs-target="#diagnosisModal">
                                    <fmt:message key="hospitalisation.determine.diagnosis"/>
                                </button>
                            </div>
                        </div>

                        <div class="modal fade" id="dischargeModal" tabindex="-1" aria-labelledby="daschargeModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="dischargeModalLabel">
                                            <fmt:message key="hospitalisation.discharge.patient"/>
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <form action="api" method="post" class="needs-validation" novalidate>
                                        <input type="hidden" name="command" value="discharge-patient">
                                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                                        <input type="hidden" name="hospitalisationId"
                                               value="${requestScope.hospitalisation.id}">
                                        <div class="modal-body">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label class="form-label">
                                                        <fmt:message key="hospitalisation.discharging.date"/>
                                                    </label>
                                                    <div class="input-group date insertInfo" data-provide="datepicker">
                                                        <input type="text" class="form-control" name="endDate"
                                                               id="endDate" required>
                                                        <div class="input-group-addon close-button">
                                                            <span class="glyphicon glyphicon-th"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="status" class="form-label">
                                                        <fmt:message key="common.status"/>
                                                    </label>
                                                    <input type="text" name="status" id="discharged"
                                                           value="<fmt:message key="hospitalisation.discharged"/>"
                                                           class="form-control" readonly="readonly"
                                                           placeholder="<fmt:message key="hospitalisation.discharged"/>"
                                                     required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-danger"
                                                    data-bs-dismiss="modal">
                                                <fmt:message key="common.close"/>
                                            </button>
                                            <button type="submit" class="btn btn-outline-primary me-2">
                                                <fmt:message key="common.save"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="diagnosisModal" tabindex="-1" aria-labelledby="diagnosisModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="diagnosisModalLabel">
                                            <fmt:message key="hospitalisation.determine.diagnosis"/>
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <form action="api" method="post" class="needs-validation" novalidate>
                                        <input type="hidden" name="command" value="determine-diagnosis">
                                        <input type="hidden" name="patientId" value="${requestScope.patientId}">
                                        <input type="hidden" name="hospitalisationId"
                                               value="${requestScope.hospitalisation.id}">
                                        <div class="modal-body">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label class="form-label">
                                                        <fmt:message key="hospitalisation.diagnosis"/>
                                                    </label>
                                                    <textarea class="form-control" rows="3" name="diagnosis" required></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">
                                                <fmt:message key="common.close"/>
                                            </button>
                                            <button type="submit" class="btn btn-outline-primary me-2">
                                                <fmt:message key="common.save"/>
                                            </button>
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

    <nav style="padding-top: 15px">
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <button class="nav-link <c:if test="${requestScope.activeTab == 'nav-staff'}">active</c:if>"
                        id="nav-staff-tab" data-bs-toggle="tab" data-bs-target="#nav-staff"
                        type="button" role="tab" aria-controls="nav-staff" aria-selected="true">
                    <fmt:message key="patient.assigned.staff"/>
                </button>
            </c:if>
            <button class="nav-link <c:if test="${requestScope.activeTab == 'nav-hospitalisations'}">active</c:if>"
                    id="nav-hospitalisations-tab" data-bs-toggle="tab" data-bs-target="#nav-hospitalisations"
                    type="button" role="tab" aria-controls="nav-hospitalisations" aria-selected="false">
                <fmt:message key="hospitalisation.hospitalisations"/>
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
                            <filter:filter
                                    nameLimit="staffLimit"
                                    selectedLimit="${requestScope.staffLimit}"
                                    nameOrderBy="staffOrderBy"
                                    selectedOrderBy="${requestScope.staffOrderBy}"
                                    optionsOrderBy="id, first_name, last_name, specialisation"
                                    nameDirection="staffDir"
                                    selectedDirection="${requestScope.staffDir}"
                                    locale="${sessionScope.lang}"
                            />
                        </form>
                        <div class="col-auto" style="padding-left: 15px">
                            <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#staffModal">
                                <fmt:message key="patient.assign.staff"/>
                            </button>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"><fmt:message key="common.first.name"/></th>
                            <th scope="col"><fmt:message key="common.last.name"/></th>
                            <th scope="col"><fmt:message key="common.email"/></th>
                            <th scope="col"><fmt:message key="staff.specialisation"/></th>
                            <th scope="col"><fmt:message key="common.role"/></th>
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
                    <pgn:pagination
                            offsetValue="${requestScope.staffOffset}"
                            limitName="staffLimit"
                            limitValue="${requestScope.staffLimit}"
                            orderByName="staffOrderBy"
                            orderByValue="${requestScope.staffOrderBy}"
                            dirName="staffDir"
                            dirValue="${requestScope.staffDir}"
                            pageName="staffPage"
                            pageValue="${requestScope.staffPage}"
                            numberOfPagesValue="${requestScope.staffNumberOfPages}"
                            totalCountValue="${requestScope.staffCount}"
                            api="api?command=patient&patientId=${requestScope.patientId}"
                            listSize="${requestScope.assignedStaff.size()}"
                            locale="${sessionScope.lang}"
                    />
                </div>
            </div>
        </c:if>
        <div class="tab-pane fade <c:if test="${requestScope.activeTab == 'nav-hospitalisations'}">show active</c:if>"
             id="nav-hospitalisations" role="tabpanel" aria-labelledby="nav-hospitalisations-tab">
            <div id="example_wrapper" class="dataTables_wrapper dt-bootstrap5">
                <div class="d-flex">
                    <form class="row g-3" action="api" method="get">
                        <input type="hidden" name="command" value="patient"/>
                        <input type="hidden" name="patientId" value="${requestScope.patientId}"/>
                        <filter:filter
                                nameLimit="hospitalisationsLimit"
                                selectedLimit="${requestScope.hospitalisationsLimit}"
                                nameOrderBy="hospitalisationsOrderBy"
                                selectedOrderBy="${requestScope.hospitalisationsOrderBy}"
                                optionsOrderBy="id, start_date, end_date"
                                nameDirection="hospitalisationsDir"
                                selectedDirection="${requestScope.hospitalisationsDir}"
                                locale="${sessionScope.lang}"
                        />
                    </form>
                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                        <div class="col-auto" style="padding-left: 15px">
                            <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#hospitalisationModal">
                                <fmt:message key="hospitalisation.add.hospitalisation"/>
                            </button>
                        </div>
                    </c:if>
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
                <pgn:pagination
                        offsetValue="${requestScope.hospitalisationsOffset}"
                        limitName="hospitalisationsLimit"
                        limitValue="${requestScope.hospitalisationsLimit}"
                        orderByName="hospitalisationsOrderBy"
                        orderByValue="${requestScope.hospitalisationsOrderBy}"
                        dirName="hospitalisationsDir"
                        dirValue="${requestScope.hospitalisationsDir}"
                        pageName="hospitalisationsPage"
                        pageValue="${requestScope.hospitalisationsPage}"
                        numberOfPagesValue="${requestScope.hospitalisationsNumberOfPages}"
                        totalCountValue="${requestScope.hospitalisationsCount}"
                        api="api?command=patient&patientId=${requestScope.patientId}"
                        listSize="${requestScope.hospitalisations.size()}"
                        locale="${sessionScope.lang}"
                />
            </div>
        </div>
    </div>

    <div class="modal fade" id="staffModal" tabindex="-1" aria-labelledby="staffModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staffModalLabel"><fmt:message key="patient.assign.staff.title"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="api" method="post" name="save-form" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="assign-staff-to-patient">
                    <input type="hidden" name="patientId" value="${requestScope.patientId}">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label"><fmt:message key="common.staff"/></label>
                            <select name="staffId" class="form-select form-select-md mb-3"
                                    aria-label=".form-select-md example" required>
                                <option value="" disabled selected><fmt:message key="common.choose"/></option>
                                <c:forEach items="${requestScope.notAssignedStaff}" var="staff">
                                    <option name="staffId" value="${staff.id}">
                                            ${staff.firstName} ${staff.lastName}, ${staff.specialisation}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">
                            <fmt:message key="common.close"/>
                        </button>
                        <button type="submit" class="btn btn-outline-primary me-2">
                            <fmt:message key="common.assign"/>
                        </button>
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
                    <h1 class="modal-title fs-5" id="hospitalisationModalLabel">
                        <fmt:message key="hospitalisation.add.hospitalisation"/>
                    </h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="api" method="post" name="save-form" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="save-hospitalisation">
                    <input type="hidden" name="patientId" value="${requestScope.patientId}">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">
                                <fmt:message key="hospitalisation.date"/>
                            </label>
                            <div class="input-group date insertInfo" data-provide="datepicker">
                                <input type="text" class="form-control" name="startDate" id="startDate" required>
                                <div class="input-group-addon close-button">
                                    <span class="glyphicon glyphicon-th"></span>
                                </div>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label"><fmt:message key="common.status"/></label>
                            <input type="text" name="status" id="status"
                                   value="<fmt:message key="hospitalisation.hospitalised"/>"
                                   class="form-control" readonly="readonly"
                                   placeholder="<fmt:message key="hospitalisation.hospitalised"/>" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">
                            <fmt:message key="common.close"/>
                        </button>
                        <button type="submit" class="btn btn-outline-primary me-2">
                            <fmt:message key="common.add"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>