<%@ page contentType="text/html;charset=cp1251" pageEncoding="Cp1251" language="java" %>
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
    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/medicine">Medicine</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/treatmentCourses">Treatment Courses</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/guestPatient">Guest Patients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/patient">Patients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/inpatients">Inpatients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor">Personal Account</a></li>
</ul>

<h3>Choose Patient</h3>
<br/>
<form action="${pageContext.request.contextPath}/personalDoctor/guestPatient" method="post">
    <label for="patientId">Enter patient ¹</label>
    <input type="number" id="patientId" name="patientId">
    <input type="submit" value="Confirm">
</form>

<h3>Guest Patients</h3>
<c:if test="${guestPatients != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Patient Name</th>
            <th>Medical Service</th>
            <th>Visit Date</th>
        </tr>
        <c:forEach items="${guestPatients}" var="guestPatient">
            <tr>
                <td></td>
                <td>${guestPatient.patientName}</td>
                <td>${guestPatient.medicalService}</td>
                <td>${guestPatient.visitDate}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p style="color: red">${error}</p>

</body>
</html>