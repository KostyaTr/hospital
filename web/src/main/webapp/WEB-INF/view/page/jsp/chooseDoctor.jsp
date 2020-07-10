<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Hospital</title>
    <style>
        table {
            width: 50%;
            counter-reset: row-num -1;
        }

        table tr {
            counter-increment: row-num;
        }

        table tr td:first-child::before {
            content: counter(row-num) ". ";
        }

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

<ul>
    <li><a href="${pageContext.request.contextPath}/appointment/chooseMedicalServices"><spring:message code="nav.backToMedicalServices"/></a></li>
</ul>
<br/>
<h3><spring:message code="chooseDoctor.chooseDoctor"/></h3>
<br/>
<form action="${pageContext.request.contextPath}/appointment/chooseDoctor" method="post">
    <label for="doctorId"><spring:message code="chooseDoctor.doctorsNum"/></label>
    <input type="number" id="doctorId" name="doctorId">
    <input type="submit" value="Confirm">
</form>

<h3><spring:message code="chooseDoctor.doctors"/></h3>
<c:if test="${doctors != null}">
    <table border="1">
        <tr>
            <th>№</th>
            <th><spring:message code="chooseDoctor.doctorName"/></th>
            <th><spring:message code="chooseDoctor.phoneNum"/></th>
            <th><spring:message code="chooseDoctor.email"/></th>
            <th><spring:message code="chooseDoctor.specialities"/></th>
            <th><spring:message code="chooseDoctor.department"/></th>
        </tr>
        <c:forEach items="${doctors}" var="doctor">
            <tr>
                <td></td>
                <td>${doctor.firstName} ${doctor.lastName} </td>
                <td>${doctor.phoneNumber}</td>
                <td>${doctor.email}</td>
                <td>
                    <c:forEach items="${doctor.specialityList}" var="speciality">
                        ${speciality.specialityName},
                    </c:forEach>
                </td>
                <td>${doctor.department.departmentName}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p style="color: red">${error}</p>
</body>
</html>
