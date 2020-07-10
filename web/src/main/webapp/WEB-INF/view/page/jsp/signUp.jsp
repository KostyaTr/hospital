<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <li><a href="${pageContext.request.contextPath}/login"><spring:message code="nav.panel.login"/></a></li>
    <li><a href="${pageContext.request.contextPath}/medicalServices"><spring:message code="nav.panel.medicalServices"/></a></li>
    <li><a href="${pageContext.request.contextPath}/doctors"><spring:message code="nav.panel.doctors"/></a></li>
    <li><a href="${pageContext.request.contextPath}/appointment"><spring:message code="nav.panel.appointment"/></a></li>
</ul>

<h3>Registration</h3>
<form action="${pageContext.request.contextPath}/signUp" method="post">
    <label for="login">Login</label>
    <input id="login" type="text" name="login"><br/>

    <label for="firstName">First Name</label>
    <input id="firstName" type="text" name="firstName"><br/>

    <label for="lastName">Last Name</label>
    <input id="lastName" type="text" name="lastName"><br/>

    <label for="phoneNumber">Phone number</label>
    <input id="phoneNumber" type="text" name="phoneNumber"><br/>

    <label for="email">Email</label>
    <input id="email" type="text" name="email"><br/>

    <label for="sex">Sex</label>
    <input id="sex" type="text" name="sex"><br/>

    <label for="address">Address</label>
    <input id="address" type="text" name="address"><br/>

    <label for="birthday">Birthday</label>
    <input id="birthday" type="date" name="birthday"><br/>

    <label>Insurance</label>
    <input type="checkbox" name="insurance" value="true"/><br/>

    <label for="password">Password</label>
    <input id="password" type="password" name="password">

    <label for="passwordRepeat">Repeat your password</label>
    <input id="passwordRepeat" type="password" name="passwordRepeat"><br/>

    <input type="submit" value="submit">
</form>

<p style="color: red">${inputError}</p>

<p style="color: red">${loginError}</p>

<p style="color: red">${passwordError}</p>
</body>
</html>