<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Hospital</title>
    <style>
        .navigation ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333333;
        }

        .navigation li {
            float: right;
        }

        .navigation li a {
            display: block;
            color: white;
            text-align: center;
            padding: 16px;
            text-decoration: none;
        }

        .navigation li a:hover {
            background-color: #111111;
        }
    </style>
</head>
<body>

<nav class="navigation">
    <ul>
        <sec:authorize access="!isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/signUp"><spring:message code="nav.panel.signUp"/></a></li>
            <li><a href="${pageContext.request.contextPath}/login"><spring:message code="nav.panel.login"/></a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/logout"><spring:message code="nav.panel.logout"/></a></li>
        </sec:authorize>
        <li><a href="${pageContext.request.contextPath}/medicalServices"><spring:message code="nav.panel.medicalServices"/></a></li>
        <li><a href="${pageContext.request.contextPath}/doctors"><spring:message code="nav.panel.doctors"/></a></li>
        <li><a href="${pageContext.request.contextPath}/appointment"><spring:message code="nav.panel.appointment"/></a></li>
        <sec:authorize access="isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/personalAccount"><spring:message code="nav.panel.personalAccount"/></a></li>
        </sec:authorize>
    </ul>
</nav>

<br/>

<c:if test="${visitTime == null}">
    <ol>
        <li><spring:message code="makeAppointment.usersName"/>: ${user.firstName} ${user.lastName}</li>
        <li><spring:message code="makeAppointment.usersPhoneNum"/>: ${user.phoneNumber}</li>
        <li><spring:message code="makeAppointment.usersEmail"/>: ${user.email}</li>
        <li><spring:message code="makeAppointment.usersServiceName"/>: ${medicalService.serviceName}</li>
        <li><spring:message code="makeAppointment.usersServiceCost"/>: ${medicalService.serviceCost} bel.rub.</li>
        <li><spring:message code="makeAppointment.usersDoctor"/>: ${doctor.firstName} ${doctor.lastName}, specialities:
            <c:forEach items="${doctor.specialityList}" var="speciality">
                ${speciality.specialityName},
            </c:forEach>
        </li>
    </ol>

    <form action="${pageContext.request.contextPath}/appointment" method="post">
        <label for="visitDay"><spring:message code="makeAppointment.chooseAppointmentDay"/>:</label>
        <select id="visitDay" name="visitDay">
            <c:if test="${availableDays != null}">
                <c:forEach items="${availableDays}" var="day">
                    <option>${day}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" value="Select" name="selectDayButton"><br/>
        <c:if test="${patientId != null}">
            <input type="submit" value="Cancel Rescheduling" name="cancelRescheduling">
        </c:if>
    </form>
    <c:if test="${patientId == null}">
        <ul>
            <li><a href="${pageContext.request.contextPath}/appointment/chooseMedicalServices">Back To Medical Services</a></li>
            <li><a href="${pageContext.request.contextPath}/appointment/chooseDoctor">Back To Doctors</a></li>
        </ul>
    </c:if>
</c:if>

<c:if test="${visitTime != null}">
    <h4><spring:message code="makeAppointment.appointmentTime"/><c:out value=" ${visitTime}"/></h4>
    <form action="${pageContext.request.contextPath}/appointment" method="post">
        <input type="submit" value="Confirm"><br/>
    </form>
    <a href="${pageContext.request.contextPath}/appointment"><spring:message code="makeAppointment.selectAnotherAppointmentDay"/></a>
</c:if>


<p style="color: red">${error}</p>


</body>
</html>
