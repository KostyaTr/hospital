<%@ page contentType="text/html;charset=cp1251" pageEncoding="Cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Hospital</title>
    <style>
        .rowCount table {
            width: 50%;
            counter-reset: row-num -1;
        }

        .rowCount table tr {
            counter-increment: row-num;
        }

        .rowCount table tr td:first-child::before {
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
    <c:if test="${inpatient == null}">
        <label for="inpatientId">Enter inpatient ¹</label>
        <input type="number" id="inpatientId" name="inpatientId">
        <input type="submit" name="cardInfo" value="Get Card Info">
    </c:if>
    <br/>

    <c:if test="${inpatient != null}">
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

        <br/>
        <input type="submit" value="Select Another Inpatient" name="selectAnotherPatient">
    </c:if>

</form>

<p style="color: red">${error}</p>

<c:if test="${inpatient == null}">
    <h3>Inpatients</h3>
    <c:if test="${inpatients != null}">
        <nav class="rowCount">
            <table border="1">
                <tr>
                    <th>¹</th>
                    <th>Patient Name</th>
                    <th>Chamber Num</th>
                    <th>Department</th>
                    <th>Diagnose</th>
                    <th>Medicine Name</th>
                    <th>Medicine Dose</th>
                    <th>Status</th>
                    <th>Enrollment Date</th>
                </tr>
                <c:forEach items="${inpatients}" var="inpatient">
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
                            <td>No medicine</td>
                            <td>No medicine</td>
                        </c:if>
                        <td>${inpatient.status}</td>
                        <td>${inpatient.enrollmentDate}</td>
                    </tr>
                </c:forEach>
            </table>
        </nav>
    </c:if>
</c:if>

<c:if test="${inpatient != null}">
    <h3>Patient Info</h3>
    <nav class="rowCount">
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
                <th>Card History</th>
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
                <td>${card.history}</td>
                <td>${inpatient.enrollmentDate}</td>
            </tr>
        </table>
    </nav>
</c:if>

</body>
</html>