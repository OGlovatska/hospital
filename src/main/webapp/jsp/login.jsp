<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html lang="${sessionScope.lang}">
<jsp:include page="fragments/head.jsp"/>
<body>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<jsp:include page="fragments/header.jsp"/>
<div class="jumbotron py-0">
    <div class="container">
        <c:if test="${sessionScope.user eq null}">
            <div class="pt-2">
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('admin@gmail.com', 'password')">
                    Admin
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('doctor@gmail.com', 'password')">
                    Doctor
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('nurse@gmail.com', 'password')">
                    Nurse
                </button>
                <button type="submit" class="btn btn-outline-primary me-2" onclick="login('patient@gmail.com', 'password')">
                    Patient
                </button>
            </div>
        </c:if>
    </div>
</div>
<div class="container">
    <div class="lead py"><br>
        <h5>PROJECT DESCRIPTION</h5>
        The task of the final project is to develop a web application that supports the functionality according to
        the task
        variant.
        <h5>Variants</h5>
        The system administrator has access to a list of doctors by category (pediatrician,
        traumatologist, surgeon, ...) and a list of patients. Implement the ability to sort:<br>
        <ul>
            <li>patients:
                <ul>
                    <li>alphabetically;</li>
                    <li>by date of birth;</li>
                </ul>
            </li>
            <li>doctors:
                <ul>
                    <li>alphabetically;</li>
                    <li>by category;</li>
                    <li>by number of patients.</li>
                </ul>
            </li>
        </ul>
        The administrator registers patients and doctors in the system and appoints a doctor to the
        patient. <br>
        The doctor determines the diagnosis, makes appointments to the patient (procedures,
        medications, operations), which are recorded in the Hospital Card. <br>
        The appointment can be made by a Nurse (procedures, medications) or a Doctor (any appointment). <br>
        The patient can be discharged from the hospital, with a definitive diagnosis recorded. <br>
        (Optional: implement the ability to save / export a document with information about the
        patient&#39;s discharge).
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    function login(email, password) {
        setCredentials(email, password);
        $("#login_form").submit();
    }

    function setCredentials(email, password) {
        $('input[name="email"]').val(email);
        $('input[name="password"]').val(password);
    }
</script>
</body>
</html>
