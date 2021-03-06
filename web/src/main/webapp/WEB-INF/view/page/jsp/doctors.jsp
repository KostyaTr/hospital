<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title>Hospital</title>
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333333;
        }

        li {
            float: right;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 16px;
            text-decoration: none;
        }

        li a:hover {
            background-color: #111111;
        }
    </style>
</head>
<body>

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

<h3>Doctors</h3>
<c:if test="${doctors != null}">
    <table>
        <tr>
            <th>№</th>
            <th>Doctor Name</th>
            <th>Phone Number</th>
            <th>Email</th>
            <th>Specialities</th>
            <th>Department</th>
        </tr>
        <c:forEach items="${doctors}" var="doctor">
            <tr>
                <td>${doctor.doctorId}</td>
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

</body>
</html>