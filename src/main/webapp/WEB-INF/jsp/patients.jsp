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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet">

    <script>
        $(document).ready(function() {
            $('.date').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true
            });

            $('.close-button').unbind();

            $('.close-button').click(function() {
                if ($('.datepicker').is(":visible")) {
                    $('.date').datepicker('hide');
                } else {
                    $('.date').datepicker('show');
                }
            });

            $('#exampleModal').on('hidden.bs.modal', function () {
                $('#exampleModal form')[0].reset();
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div id="example_wrapper" class="dataTables_wrapper dt-bootstrap5">
        <div class="d-flex">
            <form class="row g-3" action="api" method="get">
                <input type="hidden" name="command" value="patients-list"/>
                <div class="col-auto">
                    <select name="limit" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="10" ${requestScope.limit eq "10" ? "selected" : ""}>10</option>
                        <option value="25" ${requestScope.limit eq "25" ? "selected" : ""}>25</option>
                        <option value="50" ${requestScope.limit eq "50" ? "selected" : ""}>50</option>
                        <option value="100"  ${requestScope.limit eq "100" ? "selected" : ""}>100</option>
                    </select>
                </div>
                <div class="col-auto">
                    <select name="orderBy" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="" selected disabled><fmt:message key="common.order.by"/></option>
                        <option value="id"  ${requestScope.orderBy eq "id" ? "selected" : ""}>
                            <fmt:message key="common.default"/>
                        </option>
                        <option value="first_name" ${requestScope.orderBy eq "first_name" ? "selected" : ""}>
                            <fmt:message key="common.first.name"/>
                        </option>
                        <option value="last_name" ${requestScope.orderBy eq "last_name" ? "selected" : ""}>
                            <fmt:message key="common.last.name"/>
                        </option>
                        <option value="date_of_birth" ${requestScope.orderBy eq "date_of_birth" ? "selected" : ""}>
                            <fmt:message key="patient.date.of.birth"/>
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
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <div class="col-auto" style="padding-left: 15px">
                    <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        <fmt:message key="patient.add"/>
                    </button>
                </div>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">
                                    <fmt:message key="patient.new"/>
                                </h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form action="api" method="post" name="save-form">
                                <input type="hidden" name="command" value="save-patient"/>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">
                                            <fmt:message key="common.email"/>
                                        </label>
                                        <input type="email" class="form-control" placeholder="<fmt:message key="common.email"/>"
                                               name="email" id="email" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="first_name" class="form-label">
                                            <fmt:message key="common.first.name"/>
                                        </label>
                                        <input type="text" class="form-control" name="first_name" id="first_name"
                                               placeholder="<fmt:message key="common.first.name"/>">
                                    </div>
                                    <div class="mb-3">
                                        <label for="last_name" class="form-label">
                                            <fmt:message key="common.last.name"/>
                                        </label>
                                        <input type="text" class="form-control" name="last_name" id="last_name"
                                               placeholder="<fmt:message key="common.last.name"/>">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label"><fmt:message key="common.role"/></label>
                                        <select name="role" class="form-select form-select-md mb-3"
                                                aria-label=".form-select-md example">
                                            <option value="" selected><fmt:message key="role.patient"/></option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label"><fmt:message key="common.gender"/></label>
                                        <select name="gender" class="form-select form-select-md mb-3"
                                                aria-label=".form-select-md example">
                                            <option value="" disabled selected><fmt:message key="common.choose"/></option>
                                            <option value="MALE"><fmt:message key="common.gender.male"/></option>
                                            <option value="FEMALE"><fmt:message key="common.gender.female"/></option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label"><fmt:message key="patient.date.of.birth"/></label>
                                        <div class="input-group date insertInfo" data-provide="datepicker">
                                            <input type="text" class="form-control" name="date_of_birth" id="date">
                                            <div class="input-group-addon close-button">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </div>
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
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="common.first.name"/></th>
                <th scope="col"><fmt:message key="common.last.name"/></th>
                <th scope="col"><fmt:message key="common.email"/></th>
                <th scope="col"><fmt:message key="patient.gender"/></th>
                <th scope="col"><fmt:message key="patient.date.of.birth"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="patient" items="${requestScope.patients}">
                <tr onclick="document.location='api?command=patient&patientId=${patient.id}';" onmouseover=""
                    style="cursor: pointer;">
                    <td><c:out value="${patient.id}"/></td>
                    <td><c:out value="${patient.firstName}"/></td>
                    <td><c:out value="${patient.lastName}"/></td>
                    <td><c:out value="${patient.email}"/></td>
                    <td><c:out value="${patient.gender}"/></td>
                    <td><c:out value="${patient.dateOfBirth}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <div class="col-sm-12 col-md-5">
                <div class="dataTables_info" id="example_info" role="status" aria-live="polite">
                    <fmt:message key="pagination.showing"/>
                    <c:choose>
                        <c:when test="${fn:length(requestScope.patients) eq 0}">
                            0 <fmt:message key="pagination.entries"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="pagination.from"/>${requestScope.offset + 1}
                            <fmt:message key="pagination.to"/>
                            <c:choose>
                                <c:when test="${fn:length(requestScope.patients) < requestScope.limit}">
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
                <div class="dataTables_paginate paging_simple_numbers" id="example_paginate">
                    <ul class="pagination">
                        <c:if test="${requestScope.totalCount > requestScope.limit}">
                            <c:choose>
                                <c:when test="${requestScope.page > 1}">
                                    <li class="paginate_button page-item previous" id="example_previous">
                                        <a href="api?command=patients-list&page=${requestScope.page - 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
                                           aria-controls="example" data-dt-idx="previous" tabindex="0"
                                           class="page-link"><fmt:message key="pagination.previous"/></a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="paginate_button page-item previous disabled" id="example_previous">
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
                                               href="api?command=patients-list&page=${i}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${requestScope.page < requestScope.numberOfPages}">
                                    <li class="paginate_button page-item next" id="example_next">
                                        <a href="api?command=patients-list&page=${requestScope.page + 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
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