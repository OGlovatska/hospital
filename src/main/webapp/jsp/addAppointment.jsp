<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<head>
    <jsp:include page="fragments/head.jsp"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">

    <script>
        $(function () {
            $('#datetimepicker2').datetimepicker({
                format: 'YYYY-MM-DD HH:mm',
            });
        });
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<body class="bg-color">
<section class="container mt-5">
    <div class="row justify-content-md-center">
        <form class="col-md-6 col-sm-12 bg-white p-5 rounded shadow" action="api" method="post">
            <input type="hidden" name="command" value="save-appointment"/>
            <input type="hidden" name="staffId" value="${requestScope.staffId}"/>
            <div class="col-12 text-center">
                <h3 class="text-primary"><strong>Create appointment</strong></h3>
            </div>
            <div class="mb-3">
                <label class="form-label">Patient</label>
                <select name="patientId" class="form-select form-select-md mb-3"
                        aria-label=".form-select-md example">
                    <option value="" disabled selected>Please choose</option>
                    <c:forEach items="${requestScope.assignedPatients}" var="patient">
                        <option name="patientId" value="${patient.id}">
                                ${patient.firstName} ${patient.lastName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Appointment type</label>
                <select name="type" class="form-select form-select-md mb-3"
                        aria-label=".form-select-md example">
                    <option value="" disabled selected>Please choose</option>
                         <c:forEach items="${requestScope.appointmentTypes}" var="type">
                            <option value="${type}">${type}</option>
                        </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Appointment status</label>
                <select name="status" class="form-select form-select-md mb-3"
                        aria-label=".form-select-md example">
                    <option value="" disabled selected>Please choose</option>
                    <c:forEach items="${requestScope.appointmentStatuses}" var="status">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea class="form-control" rows="3" name="description"></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Conclusion</label>
                <textarea class="form-control" rows="3" name="conclusion"></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Date and time</label>
                <div class='input-group date' id='datetimepicker2'>
                    <input type='text' class="form-control" name="dateTime"/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                </div>
            </div>
            <div class="text-center mt-3">
                <button type="submit" class="btn btn-lg btn-block btn-primary">Submit</button>
            </div>
        </form>
    </div>
</section>
</body>
</html>