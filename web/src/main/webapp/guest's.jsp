<%@ page contentType="text/html;charset=Cp1251" pageEncoding="Cp1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <li><a href="${pageContext.request.contextPath}/signUp">Sign Up</a></li>
    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
    <li><a href="${pageContext.request.contextPath}/medicalServices">Medical Services</a></li>
    <li><a href="${pageContext.request.contextPath}/doctors">Doctors</a></li>
    <li><a href="${pageContext.request.contextPath}/appointment">Make appointment</a></li>
</ul>

</body>

<c:if test="${coupon != null}">
    <h3>Appointment made! Your number in line: ${coupon}</h3>
</c:if>

<c:if test="${coupon == null}">
    <h3>Make an appointment</h3>
    <form action="${pageContext.request.contextPath}/guestPage" method="post">
        <label for="firstName">First Name</label>
        <input id="firstName" type="text" name="firstName"><br/>

        <label for="lastName">Last Name</label>
        <input id="lastName" type="text" name="lastName"><br/>

        <label for="email">Email</label>
        <input id="email" type="text" name="email"><br/>

        <label for="phone">Phone</label>
        <input id="phone" type="text" name="phone"><br/>

        <input type="submit" value="Confirm">
    </form>
</c:if>

<br/>
<p style="color: red">${incorrectInput}</p>
</html>


