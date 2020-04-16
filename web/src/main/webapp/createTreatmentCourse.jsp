<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Hospital</title>
    <style>
        input[type="checkbox"]{
            width: 15px;
            height: 15px;
            cursor: pointer;
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
<h3>Create Treatment Course</h3>
<form action="${pageContext.request.contextPath}/personalDoctor/createTreatmentCourse" method="post">
    <label for="medicineName">Medicine Name</label>
    <select id="medicineName" name="medicineName">
        <c:if test="${medicine != null}">
            <c:forEach items="${medicine}" var="medicine">
                <option>${medicine.medicineName}</option>
            </c:forEach>
        </c:if>
    </select><br/>

    <label for="drugDose">Enter Drug Dose</label>
    <input id="drugDose" type="number" name="drugDose">
    <c:if test="${overdose != null}">
        <label style="color: red" for="drugDose">Critical Dose For That Drug Is: ${medicineDose}</label>
    </c:if>
    <br/>

    <label for="timesPerDay">Times a Day</label>
    <input type="number" id="timesPerDay" name="timesPerDay"><br/>

    <label for="durationInDays">Duration in Days</label>
    <input type="number" id="durationInDays" name="durationInDays"><br/>

    <label for="receptionDescription">Reception Description</label>
    <input type="text" id="receptionDescription" name="receptionDescription"><br/>


    <c:if test="${overdose != null}">
        <label style="color: red" for="overdose">${overdose}</label>
        <input type="checkbox" name="overdose" id="overdose" value="true"><br/>
    </c:if>

    <input type="submit" value="Confirm">
</form>

<p style="color: red">${error}</p>

</body>
</html>