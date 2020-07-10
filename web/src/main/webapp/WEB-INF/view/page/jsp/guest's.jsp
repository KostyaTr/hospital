<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    <li><a href="${pageContext.request.contextPath}/signUp"><spring:message code="nav.panel.signUp"/></a></li>
    <li><a href="${pageContext.request.contextPath}/login"><spring:message code="nav.panel.login"/></a></li>
    <li><a href="${pageContext.request.contextPath}/medicalServices"><spring:message code="nav.panel.medicalServices"/></a></li>
    <li><a href="${pageContext.request.contextPath}/doctors"><spring:message code="nav.panel.doctors"/></a></li>
    <li><a href="${pageContext.request.contextPath}/appointment"><spring:message code="nav.panel.appointment"/></a></li>
</ul>

</body>

<c:if test="${coupon != null}">
    <h3>Appointment made! Your number in line: ${coupon}</h3>
</c:if>

<c:if test="${coupon == null}">
    <h3>Make an appointment</h3>
    <form action="${pageContext.request.contextPath}/appointment/guestInfo" method="post">
        <label for="firstName">First Name</label>
        <input id="firstName" type="text" name="firstName"><br/>

        <label for="lastName">Last Name</label>
        <input id="lastName" type="text" name="lastName"><br/>

        <label for="email">Email</label>
        <input id="email" type="text" name="email"><br/>

        <label for="phoneNumber">Phone</label>
        <input id="phoneNumber" type="text" name="phoneNumber"><br/>

        <input type="submit" value="Confirm">
    </form>
</c:if>

<br/>
<p style="color: red">${incorrectInput}</p>
</html>


