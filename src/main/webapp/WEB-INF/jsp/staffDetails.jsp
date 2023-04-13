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
    <script type="text/javascript" src="resources/js/common.js" defer></script>

    <script>
        $(document).ready(function() {
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
                                <input type="text" class="form-control" id="firstName" placeholder="${requestScope.currentStaff.firstName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="lastName"><fmt:message key="common.last.name"/></label>
                                <input type="text" class="form-control" id="lastName" placeholder="${requestScope.currentStaff.lastName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="eMail"><fmt:message key="common.email"/></label>
                                <input type="email" class="form-control" id="eMail" placeholder="${requestScope.currentStaff.email}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="role"><fmt:message key="common.role"/></label>
                                <input type="text" class="form-control" id="role" placeholder="${requestScope.currentStaff.role}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label for="specialisation"><fmt:message key="staff.specialisation"/></label>
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
            <button class="nav-link active" id="nav-patients-tab" data-bs-toggle="tab" data-bs-target="#nav-patients"
                    type="button" role="tab" aria-controls="nav-patients" aria-selected="true">
                <fmt:message key="staff.assigned.patient"/>
            </button>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent" style="padding-top: 15px">
        <div class="tab-pane fade show active"
             id="nav-patients" role="tabpanel" aria-labelledby="nav-patients-tab">
            <div class="dataTables_wrapper dt-bootstrap5">
                <div class="d-flex">
                    <form class="row g-3" action="api" method="get">
                        <input type="hidden" name="command" value="staff"/>
                        <input type="hidden" name="staffId" value="${requestScope.staffId}"/>
                        <filter:filter
                                nameLimit="limit"
                                selectedLimit="${requestScope.limit}"
                                nameOrderBy="orderBy"
                                selectedOrderBy="${requestScope.orderBy}"
                                optionsOrderBy="id, first_name, last_name, date_of_birth"
                                nameDirection="dir"
                                selectedDirection="${requestScope.dir}"
                                locale="${sessionScope.lang}"
                        />
                    </form>
                    <div class="col-auto" style="padding-left: 15px">
                        <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                                data-bs-target="#patientModal">
                            <fmt:message key="staff.assign.patient"/>
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
                        <th scope="col"><fmt:message key="patient.date.of.birth"/></th>
                        <th scope="col"><fmt:message key="patient.gender"/></th>
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
                <pgn:pagination
                        offsetValue="${requestScope.offset}"
                        limitValue="${requestScope.limit}"
                        orderByValue="${requestScope.orderBy}"
                        dirValue="${requestScope.dir}"
                        pageValue="${requestScope.page}"
                        numberOfPagesValue="${requestScope.numberOfPages}"
                        totalCountValue="${requestScope.totalCount}"
                        api="api?command=staff&staffId=${requestScope.staffId}"
                        listSize="${requestScope.assignedPatients.size()}"
                        locale="${sessionScope.lang}"
                />
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="patientModal" tabindex="-1" aria-labelledby="patientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5"><fmt:message key="staff.assign.patient.title"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="api" method="post" name="save-form" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="assign-patient-to-staff">
                <input type="hidden" name="staffId" value="${requestScope.staffId}">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label"><fmt:message key="patient.patient"/></label>
                        <select name="patientId" class="form-select form-select-md mb-3"
                                aria-label=".form-select-md example" required>
                            <option value="" disabled selected><fmt:message key="common.choose"/></option>
                            <c:forEach items="${requestScope.notAssignedPatients}" var="patient">
                                <option name="patientId" value="${patient.id}">
                                        ${patient.firstName} ${patient.lastName}
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
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>