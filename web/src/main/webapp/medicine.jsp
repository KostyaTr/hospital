<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/medicine">Medicine</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/treatmentCourses">Treatment Courses</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/guestPatients">Guest Patients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/patients">Patients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor/inpatients">Inpatients</a></li>
    <li><a href="${pageContext.request.contextPath}/personalDoctor">Personal Account</a></li>
</ul>

<h3>Medicine</h3>
<c:if test="${medicine != null}">
    <table border="1">
        <tr>
            <th>№</th>
            <th>Medicine Name</th>
            <th>Normal Dose</th>
            <th>Critical Dose</th>
            <th>Package Amount</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${medicine}" var="medicine">
            <tr>
                <td>${medicine.medicineId}</td>
                <td>${medicine.medicineName}</td>
                <td>${medicine.normalDose}</td>
                <td>${medicine.criticalDose}</td>
                <td>${medicine.packageAmount}</td>
                <td>${medicine.price}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>