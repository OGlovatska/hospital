<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>

    <jsp:include page="fragments/head.jsp"/>
    <script>
        $(document).ready(function() {
            $('#exampleModal').on('hidden.bs.modal', function () {
                $('#exampleModal form')[0].reset();
            });
        });
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="container">
    <div id="example_wrapper" class="dataTables_wrapper dt-bootstrap5">
        <div class="d-flex">
            <form class="row g-3" action="api" method="get">
                <input type="hidden" name="command" value="staff-list"/>
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
                        <option value="" disabled selected>Order by</option>
                        <option value="specialisation" ${requestScope.orderBy eq "specialisation" ? "selected" : ""}>
                            Specialisation
                        </option>
                        <option value="first_name" ${requestScope.orderBy eq "first_name" ? "selected" : ""}>
                            First name
                        </option>
                        <option value="last_name" ${requestScope.orderBy eq "last_name" ? "selected" : ""}>
                            Last name
                        </option>
                        <option value="patients" ${requestScope.orderBy eq "patients" ? "selected" : ""}>
                            Patients
                        </option>
                    </select>
                </div>
                <div class="col-auto">
                    <select name="dir" aria-controls="example" class="form-select form-select-sm" onchange=submit()>
                        <option value="" disabled selected>Direction</option>
                        <option value="ASC" ${requestScope.dir eq "ASC" ? "selected" : ""}>Ascending</option>
                        <option value="DESC" ${requestScope.dir eq "DESC" ? "selected" : ""}>Descending</option>
                    </select>
                </div>
            </form>
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">Create staff</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form action="api" method="post" name="save-form">
                                <input type="hidden" name="command" value="save-staff"/>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email address</label>
                                        <input type="email" class="form-control" placeholder="Email" name="email" id="email" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="first_name" class="form-label">First name</label>
                                        <input type="text" class="form-control" placeholder="First name" name="first_name" id="first_name">
                                    </div>
                                    <div class="mb-3">
                                        <label for="last_name" class="form-label">Last name</label>
                                        <input type="text" class="form-control" placeholder="Last name" name="last_name" id="last_name">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Role</label>
                                        <select name="role" class="form-select form-select-md mb-3" aria-label=".form-select-md example">
                                            <c:forEach items="${roles}" var="role">
                                                <option value="${role}">${role}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Specialisation</label>
                                        <select name="specialisation" class="form-select form-select-md mb-3" aria-label=".form-select-md example">
                                            <c:forEach items="${specialisations}" var="specialisation">
                                                <option value="${specialisation}">${specialisation}</option>
                                            </c:forEach>
                                        </select>
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
                <div class="col-auto" style="padding-left: 15px">
                    <button type="submit" class="btn btn-outline-primary me-2 btn-sm" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        Add staff
                    </button>
                </div>
            </c:if>
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
                <th scope="col">Number of patients</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="staff" items="${requestScope.staff}">
                <tr onclick="document.location='api?command=staff&staffId=${staff.id}';" onmouseover=""
                    style="cursor: pointer;">
                    <td><c:out value="${staff.id}"/></td>
                    <td><c:out value="${staff.firstName}"/></td>
                    <td><c:out value="${staff.lastName}"/></td>
                    <td><c:out value="${staff.email}"/></td>
                    <td><c:out value="${staff.specialisation}"/></td>
                    <td><c:out value="${staff.role}"/></td>
                    <td><c:out value="${staff.patients}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <div class="col-sm-12 col-md-5">
                <div class="dataTables_info" id="example_info" role="status" aria-live="polite">
                    Showing
                    <c:choose>
                        <c:when test="${fn:length(requestScope.staff) eq 0}"> 0 entries</c:when>
                        <c:otherwise> ${requestScope.offset + 1} to
                            <c:choose>
                                <c:when test="${fn:length(requestScope.staff) < requestScope.limit}">
                                    ${requestScope.totalCount}
                                </c:when>
                                <c:otherwise>
                                    ${requestScope.offset + requestScope.limit}
                                </c:otherwise>
                            </c:choose>
                            of ${requestScope.totalCount} entries
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
                                        <a href="api?command=staff-list&page=${requestScope.page - 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
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
                                               href="api?command=staff-list&page=${i}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${requestScope.page < requestScope.numberOfPages}">
                                    <li class="paginate_button page-item next" id="example_next">
                                        <a href="api?command=staff-list&page=${requestScope.page + 1}&limit=${requestScope.limit}&orderBy=${requestScope.orderBy}&dir=${requestScope.dir}"
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
<jsp:include page="fragments/footer.jsp"/></body>
</html>
