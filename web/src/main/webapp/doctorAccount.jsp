<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor/medicine">Medicine</a></li>
            <li><a href="${pageContext.request.contextPath}/personalDoctor/treatmentCourses">Treatment Courses</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor/guestPatient">Guest Patients</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor/patient">Patients</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor/inpatients">Inpatients</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor">Personal Account</a></li>
        <li><a href="${pageContext.request.contextPath}/personalDoctor/dischargedInpatients">Discharged Inpatients</a></li>
    </ul>
</nav>

<h2>Welcome back, <c:out value="${name}"/> </h2>

<h3><c:out value="${patient}"/></h3>

</body>
</html>