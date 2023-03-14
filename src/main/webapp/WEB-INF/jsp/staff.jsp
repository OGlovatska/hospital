<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" uri="/WEB-INF/pagination.tld" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>

    <script>
        $(document).ready(function() {
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
                        <option value="" selected disabled><fmt:message key="common.order.by"/></option>
                        <option value="id"  ${requestScope.orderBy eq "id" ? "selected" : ""}>
                            <fmt:message key="common.default"/>
                        </option>
                        <option value="specialisation" ${requestScope.orderBy eq "specialisation" ? "selected" : ""}>
                            <fmt:message key="staff.specialisation"/>
                        </option>
                        <option value="first_name" ${requestScope.orderBy eq "first_name" ? "selected" : ""}>
                            <fmt:message key="common.first.name"/>
                        </option>
                        <option value="last_name" ${requestScope.orderBy eq "last_name" ? "selected" : ""}>
                            <fmt:message key="common.last.name"/>
                        </option>
                        <option value="patients" ${requestScope.orderBy eq "patients" ? "selected" : ""}>
                            <fmt:message key="staff.patients"/>
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
                        <fmt:message key="staff.add"/>
                    </button>
                </div>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">
                                    <fmt:message key="staff.new"/>
                                </h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form action="api" method="post" name="save-form">
                                <input type="hidden" name="command" value="save-staff"/>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">
                                            <fmt:message key="common.email"/>
                                        </label>
                                        <input type="email" class="form-control" name="email" id="email" required
                                               placeholder="<fmt:message key="common.email"/>">
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
                                        <select name="role" class="form-select form-select-md mb-3" aria-label=".form-select-md example">
                                            <option value="" disabled selected><fmt:message key="common.choose"/></option>
                                            <option value="DOCTOR"><fmt:message key="role.doctor"/></option>
                                            <option value="NURSE"><fmt:message key="role.nurse"/></option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label"><fmt:message key="staff.specialisation"/></label>
                                        <select name="specialisation" class="form-select form-select-md mb-3" aria-label=".form-select-md example">
                                            <option value="" disabled selected><fmt:message key="common.choose"/></option>
                                            <c:forEach items="${specialisations}" var="specialisation">
                                                <option value="${specialisation}">${specialisation}</option>
                                            </c:forEach>
                                        </select>
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
                <th scope="col"><fmt:message key="staff.specialisation"/></th>
                <th scope="col"><fmt:message key="common.role"/></th>
                <th scope="col"><fmt:message key="staff.patients"/></th>
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
        <tg:pagination
                offsetValue="${requestScope.offset}"
                limitValue="${requestScope.limit}"
                orderByValue="${requestScope.orderBy}"
                dirValue="${requestScope.dir}"
                pageValue="${requestScope.page}"
                numberOfPagesValue="${requestScope.numberOfPages}"
                totalCountValue="${requestScope.totalCount}"
                api="api?command=staff-list"
                listSize="${requestScope.staff.size()}"
                locale="${sessionScope.lang}"
        />
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
