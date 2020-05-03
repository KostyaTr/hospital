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
<br/>

<c:out value="Page number: ${page}"/>

<form action="${pageContext.request.contextPath}/personalDoctor/dischargedInpatients" method="post">
    <c:if test="${pageCount != 0}">
        <c:if test="${page != 1}">
            <input type="submit" id="previousPage" name="previousPage" value="Previous Page">
        </c:if>
        <input type="submit" id="goto" name="goto" value="Go To">
        <label for="goToPage">Page Number</label>
        <select id="goToPage" name="goToPage">
            <c:forEach var = "i" begin = "1" end = "${pageCount}">
                <option value = "${i}" ${i == page ? 'selected="selected"' : ''}>${i}</option>
            </c:forEach>
        </select>
        <c:if test="${page != pageCount}">
            <input type="submit" id="nextPage" name="nextPage" value="Next Page"  />
        </c:if>
    </c:if>
</form>

<br/>
<h3>Discharged Inpatients</h3>
<c:if test="${dischargeInpatients != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Patient Name</th>
            <th>Doctor Name</th>
            <th>Diagnose</th>
            <th>Card History</th>
            <th>Treatment Course</th>
            <th>Patient Status</th>
            <th>Enrollment Date</th>
            <th>Discharge Date</th>
        </tr>
        <c:forEach items="${dischargeInpatients}" var="dischargeInpatient">
            <tr>
                <td></td>
                <td>${dischargeInpatient.patientName}</td>
                <td>${dischargeInpatient.doctorName}</td>
                <td>${dischargeInpatient.diagnose}</td>
                <td>${dischargeInpatient.cardHistory}</td>
                <td>${dischargeInpatient.treatmentCourse}</td>
                <td>${dischargeInpatient.status}</td>
                <td>${dischargeInpatient.enrollmentDate}</td>
                <td>${dischargeInpatient.dischargeDate}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br/>
<c:out value="Page number: ${page}"/>
<br/>
<form action="${pageContext.request.contextPath}/personalDoctor/dischargedInpatients" method="post">
    <c:if test="${pageCount != 0}">
        <c:if test="${page != 1}">
            <input type="submit" id="previousPage" name="previousPage" value="Previous Page">
        </c:if>
        <input type="submit" id="goto" name="goto" value="Go To">
        <label for="goToPage">Page Number</label>
        <select id="goToPage" name="goToPage">
            <c:forEach var = "i" begin = "1" end = "${pageCount}">
                <option value = "${i}" ${i == page ? 'selected="selected"' : ''}>${i}</option>
            </c:forEach>
        </select>
        <c:if test="${page != pageCount}">
            <input type="submit" id="nextPage" name="nextPage" value="Next Page"  />
        </c:if>
    </c:if>
</form>


</body>
</html>