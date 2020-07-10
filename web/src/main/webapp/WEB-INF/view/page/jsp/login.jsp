<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <li><a href="${pageContext.request.contextPath}/signUp"><spring:message code="nav.panel.signUp"/></a></li>
    <li><a href="${pageContext.request.contextPath}/medicalServices"><spring:message code="nav.panel.medicalServices"/></a></li>
    <li><a href="${pageContext.request.contextPath}/doctors"><spring:message code="nav.panel.doctors"/></a></li>
    <li><a href="${pageContext.request.contextPath}/appointment"><spring:message code="nav.panel.appointment"/></a></li>
</ul>

<h3>Login</h3>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="login">login</label>
    <input id="login" type="text" name="login"> <br/>

    <label for="password">password</label>
    <input id="password" type="password" name="password"><br/>
    <input type="submit" value="Login">
</form>

<p style="color: red">${error}</p>

</body>
</html>