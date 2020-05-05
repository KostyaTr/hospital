<%@ page contentType="text/html; charset=Cp1251" pageEncoding="Cp1251" language="java" %>
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
        <li><a href="${pageContext.request.contextPath}/medicalServices">Medical Services</a></li>
        <li><a href="${pageContext.request.contextPath}/doctors">Doctors</a></li>
        <li><a href="${pageContext.request.contextPath}/appointment">Make appointment</a></li>
        <li><a href="${pageContext.request.contextPath}/personalUser">Personal Account</a></li>
    </ul>
</nav>

<h2>Welcome back, <c:out value="${name}"/> </h2>

<br/>
<form action="${pageContext.request.contextPath}/personalUser" method="post">
    <label for="appointmentId">Appointment ¹</label>
    <select id="appointmentId" name="appointmentId">
        <c:forEach var = "i" begin = "1" end = "${appointmentsSize}">
            <option>${i}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Reschedule Appointment" name="reschedule">
    <input type="submit" value="Cancel Appointment" name="cancel">
</form>
<br/>

<h3>Your appointments</h3>
<ol>
    <c:forEach items="${appointments}" var="item">
        <li>
           <h3><c:out value="Number in line: ${item.couponNum} to ${item.doctorName} at ${item.visitDate}"/></h3>
       </li>
    </c:forEach>
</ol>

</body>
</html>