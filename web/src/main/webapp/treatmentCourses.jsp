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
    <li><a href="${pageContext.request.contextPath}/personalDoctor/dischargedInpatients">Discharged Inpatients</a></li>
</ul>

<h3><c:out value="${treatmentCourseCreation}"/></h3>

<a href="${pageContext.request.contextPath}/personalDoctor/createTreatmentCourse">Create New Treatment Course</a>
<br/>
<h3>Treatment Courses</h3>
<c:if test="${treatmentCourse != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Medicine Name</th>
            <th>Medicine Dose</th>
            <th>Reception Description</th>
            <th>Times per Day</th>
            <th>Duration in Days</th>
        </tr>
        <c:forEach items="${treatmentCourse}" var="treatmentCourse">
            <tr>
                <td></td>
                <td>${treatmentCourse.medicineName}</td>
                <td>${treatmentCourse.medicineDose}</td>
                <td>${treatmentCourse.receptionDesc}</td>
                <td>${treatmentCourse.timesPerDay}</td>
                <td>${treatmentCourse.durationInDays}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br/>

<a href="${pageContext.request.contextPath}/personalDoctor/createTreatmentCourse">Create New Treatment Course</a>

</body>
</html>