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
    <li><a href="${pageContext.request.contextPath}/personalDoctor/dischargedInpatients">Discharged Inpatients</a></li>
</ul>
<br/>
<form action="${pageContext.request.contextPath}/personalDoctor/inpatients" method="post">
    <label for="inpatientId">Enter inpatient ¹</label>
    <input type="number" id="inpatientId" name="inpatientId">
    <br/>

    <label for="diagnose">Update Or Set Diagnose</label>
    <input type="radio" name="option" id="diagnose" value="diagnose" checked>
    <br/>

    <label for="treatmentCourse">Prescribe New Treatment Course</label>
    <input type="radio" name="option" id="treatmentCourse" value="treatmentCourse">
    <br/>

    <label for="updateStatus">Update Status</label>
    <input type="radio" name="option" id="updateStatus" value="updateStatus">
    <br/>

    <label for="dischargeInpatient">Discharge Inpatient</label>
    <input type="radio" name="option" id="dischargeInpatient" value="dischargeInpatient">
    <br/>

    <input type="submit" value="Confirm">
</form>

<p style="color: red">${error}</p>

<h3>Inpatients</h3>
<c:if test="${inpatients != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Patient Name</th>
            <th>Chamber Num</th>
            <th>Diagnose</th>
            <th>Medicine Name</th>
            <th>Medicine Dose</th>
            <th>Status</th>
            <th>Enrollment Date</th>
        </tr>
        <c:forEach items="${inpatients}" var="inpatient">
            <tr>
                <td></td>
                <td>${inpatient.patientName}</td>
                <td>${inpatient.chamberId}</td>
                <c:if test="${inpatient.diagnose == null}">
                    <td>No diagnose</td>
                </c:if>
                <c:if test="${inpatient.diagnose != null}">
                    <td>${inpatient.diagnose}</td>
                </c:if>
                <c:if test="${inpatient.medicineName != null}">
                    <td>${inpatient.medicineName}</td>
                    <td>${inpatient.medicineDose}</td>
                </c:if>
                <c:if test="${inpatient.medicineName == null}">
                    <td>No medicine</td>
                    <td>No medicine</td>
                </c:if>
                <td>${inpatient.status}</td>
                <td>${inpatient.enrollmentDate}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>