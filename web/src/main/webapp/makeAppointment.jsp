<%@ page contentType="text/html; charset=cp1251" pageEncoding="Cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <c:if test="${authUser == null}">
            <li><a href="${pageContext.request.contextPath}/signUp">Sign Up</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
        </c:if>
        <c:if test="${authUser != null}">
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}/medicalServices">Medical Services</a></li>
        <li><a href="${pageContext.request.contextPath}/doctors">Doctors</a></li>
        <li><a href="${pageContext.request.contextPath}/appointment">Make appointment</a></li>
        <c:if test="${authUser.role == 'AuthorizedUser'}">
            <li><a href="${pageContext.request.contextPath}/personalUser">Personal Account</a></li>
        </c:if>
        <c:if test="${authUser.role == 'Doctor'}">
            <li><a href="${pageContext.request.contextPath}/personalDoctor">Personal Account</a></li>
        </c:if>
    </ul>
</nav>

<br/>

<c:if test="${visitTime == null}">
    <ol>
        <li>Your Name: ${user.firstName} ${user.lastName}</li>
        <li>Your Phone Number: ${user.phoneNumber}</li>
        <li>Your email: ${user.email}</li>
        <li>Medical Service: ${medicalService.serviceName}</li>
        <li>Service COST: ${medicalService.serviceCost} bel.rub.</li>
        <li>Appointed Doctor: ${doctor.firstName} ${doctor.lastName}, specialities:
            <c:forEach items="${doctor.specialityList}" var="speciality">
                ${speciality.specialityName},
            </c:forEach>
        </li>
    </ol>

    <form action="${pageContext.request.contextPath}/appointment" method="post">
        <label for="visitDay">Choose a day for your appointment:</label>
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
            <li><a href="${pageContext.request.contextPath}/chooseMedicalServices">Back To Medical Services</a></li>
            <li><a href="${pageContext.request.contextPath}/chooseDoctor">Back To Doctors</a></li>
        </ul>
    </c:if>
</c:if>

<c:if test="${visitTime != null}">
    <h4><c:out value="Earliest time is ${visitTime}. Is that OK?"/></h4>
    <form action="${pageContext.request.contextPath}/appointment" method="post">
        <input type="submit" value="Confirm"><br/>
    </form>
    <a href="${pageContext.request.contextPath}/appointment">Select another day</a>
</c:if>


<p style="color: red">${error}</p>


</body>
</html>
