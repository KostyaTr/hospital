<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
        <li><a href="${pageContext.request.contextPath}/logout"><spring:message code="nav.panel.logout"/></a></li>
        <li><a href="${pageContext.request.contextPath}/medicalServices"><spring:message code="nav.panel.medicalServices"/></a></li>
        <li><a href="${pageContext.request.contextPath}/doctors"><spring:message code="nav.panel.doctors"/></a></li>
        <li><a href="${pageContext.request.contextPath}/appointment"><spring:message code="nav.panel.appointment"/></a></li>
        <li><a href="${pageContext.request.contextPath}/personalAccount"><spring:message code="nav.panel.personalAccount"/></a></li>
    </ul>
</nav>

<h2>Welcome back, <c:out value="${name}"/> </h2>

<br/>
<form action="${pageContext.request.contextPath}/personalAccount" method="post">
    <label for="appointmentId">Appointment №</label>
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
           <h3><c:out value="Number in line: ${item.couponNum} to ${item.doctor.firstName} ${item.doctor.lastName} at ${item.visitDate}"/></h3>
       </li>
    </c:forEach>
</ol>

<c:if test="${receipt != null}">
    <br/>
    <h3>Your hospital bill</h3>
    <table border="1">
        <tr>
            <th>Price For Chamber</th>
            <th>Price For Medicine</th>
            <th>Total</th>
        </tr>
        <tr>
            <td>${receipt.priceForChamber}</td>
            <td>${receipt.priceForMedicine}</td>
            <td>${receipt.priceForMedicine + receipt.priceForChamber}</td>
        </tr>
    </table>
</c:if>
</body>
</html>