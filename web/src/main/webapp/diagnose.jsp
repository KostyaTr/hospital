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
<c:if test="${inpatient != null}">
    <table border="1">
        <tr>
            <th>¹</th>
            <th>Patient Name</th>
            <th>Chamber Num</th>
            <th>Department</th>
            <th>Diagnose</th>
            <c:if test="${inpatient.treatmentCourse != null}">
                <th>Medicine Name</th>
                <th>Medicine Dose</th>
            </c:if>
            <c:if test="${inpatient.treatmentCourse == null}">
                <th>Treatment Course</th>
            </c:if>
            <th>Status</th>
            <th>Enrollment Date</th>
        </tr>
        <tr>
            <td></td>
            <td>${inpatient.firstName} ${inpatient.lastName}</td>
            <td>${inpatient.chamber.chamberNum}</td>
            <td>${inpatient.chamber.department.departmentName}</td>
            <c:if test="${inpatient.diagnose == null}">
                <td>No diagnose</td>
            </c:if>
            <c:if test="${inpatient.diagnose != null}">
                <td>${inpatient.diagnose}</td>
            </c:if>
            <c:if test="${inpatient.treatmentCourse != null}">
                <td>${inpatient.treatmentCourse.medicineName}</td>
                <td>${inpatient.treatmentCourse.medicineDose}</td>
            </c:if>
            <c:if test="${inpatient.treatmentCourse == null}">
                <td>No Treatment Course</td>
            </c:if>
            <td>${inpatient.status}</td>
            <td>${inpatient.enrollmentDate}</td>
        </tr>
    </table>
</c:if>

<br/>
<form action="${pageContext.request.contextPath}/personalDoctor/diagnose" method="post">
    <label for="diagnose">Enter New Diagnose</label>
    <input type="text" id="diagnose" name="diagnose">

    <label for="prescribeTreatmentCourse">Do You Want To Prescribe Treatment Course?</label>
    <input type="checkbox" name="prescribeTreatmentCourse" id="prescribeTreatmentCourse" value="true"><br/>
    <input type="submit" value="Confirm">
</form>
<br/>

</body>
</html>