<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment-with-locales.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">

    <script type="text/javascript" src="resources/js/common.js" defer></script>
    <script>
        $(function () {
            moment.locale("${sessionScope.lang}");
            $('#datetimepicker2').datetimepicker({
                format: 'YYYY-MM-DD HH:mm',
                locale: moment.locale(),
            });
        });

        $(document).ready(function() {
            validateForm();
        });
    </script>
</head>
<body class="bg-color">
<section class="container mt-5">
    <div class="row justify-content-md-center">
        <form class="col-md-6 col-sm-12 bg-white p-5 rounded shadow needs-validation" action="api" method="post" novalidate>
            <input type="hidden" name="command" value="save-appointment"/>
            <input type="hidden" name="staffId" value="${requestScope.staffId}"/>
            <div class="col-12 text-center">
                <h3 class="text-primary"><strong><fmt:message key="appointment.create"/></strong></h3>
            </div>
            <div class="mb-3">
                <label class="form-label"><fmt:message key="patient.patient"/></label>
                <select name="patientId" class="form-select form-select-md mb-3"
                        aria-label=".form-select-md example" required>
                    <option value="" disabled selected><fmt:message key="common.choose"/></option>
                    <c:forEach items="${requestScope.assignedPatients}" var="patient">
                        <option name="patientId" value="${patient.id}">
                                ${patient.firstName} ${patient.lastName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label"><fmt:message key="common.type"/></label>
                <select name="type" class="form-select form-select-md mb-3" aria-label=".form-select-md example" required>
                    <option value="" disabled selected><fmt:message key="common.choose"/></option>
                    <option value="PROCEDURE"><fmt:message key="appointment.type.procedure"/></option>
                    <option value="MEDICATION"><fmt:message key="appointment.type.medication"/></option>
                    <c:if test="${sessionScope.user.role eq 'DOCTOR'}">
                        <option value="SURGERY"><fmt:message key="appointment.type.surgery"/></option>
                        <option value="ANALYSIS"><fmt:message key="appointment.type.analysis"/></option>
                    </c:if>
                </select>
            </div>
            <div class="mb-3">
                <div class="mb-3">
                    <label class="form-label"><fmt:message key="common.status"/></label>
                    <input type="text" name="status" value="<fmt:message key="appointment.status.assigned"/>"
                           id="status" class="form-control" readonly="readonly"
                           placeholder="<fmt:message key="appointment.status.assigned"/>" required>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label"><fmt:message key="appointment.description"/></label>
                <textarea class="form-control" rows="3" name="description" required></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label"><fmt:message key="appointment.date.time"/></label>
                <div class='input-group date' id='datetimepicker2'>
                    <input type='text' class="form-control" name="dateTime" required/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                </div>
            </div>
            <div style="padding-top: 15px">
                <div class="col-auto">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-outline-primary me-2 btn-sm float-end">
                            <fmt:message key="common.save"/>
                        </button>
                    </div>
                </div>
            </div>
            <form action="api" method="post">
                <input type="hidden" name="command" value="logout"/>
                <button type="submit" class="btn btn-outline-danger me-2 btn-sm float-end"
                        onclick="location.href='api?command=appointments-list';">
                    <fmt:message key="common.close"/>
                </button>
            </form>
        </form>

    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>