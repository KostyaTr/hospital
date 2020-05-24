<%@ page contentType="text/html; charset=Cp1251" pageEncoding="Cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Hospital</title>
    <style>
        table {
            width: 50%;
            counter-reset: row-num -1;
        }
        table  tr  {
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

<ul>
    <li><a href="${pageContext.request.contextPath}/chooseMedicalServices">Back To Medical Services</a></li>
</ul>
<br/>
<h3>Choose Doctor</h3>
<br/>
<form action="${pageContext.request.contextPath}/chooseDoctor" method="post">
    <label for="doctorId">Enter doctor's ¹</label>
    <input type="number" id="doctorId" name="doctorId">
    <input type="submit" value="Confirm">
</form>

<h3>Doctors</h3>
<c:if test="${doctors != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Doctor Name</th>
            <th>Phone Number</th>
            <th>Email</th>
            <th>Specialities</th>
            <th>Department</th>
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
