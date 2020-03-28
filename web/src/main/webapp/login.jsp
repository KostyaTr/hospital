<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>Login</h3>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="login">login</label>
    <input id="login" type="text" name="login"> <br/>

    <label for="password">password</label>
    <input id="password" type="password" name="password"><br/>
    <input type="submit">
</form>

<p style="color: red">${error}</p>

<br/>

<form action="${pageContext.request.contextPath}/guestPage" method="get">
    <input type="submit" value="Continue as a guest" name="guest" />
</form>

<br/>

<form action="${pageContext.request.contextPath}/signUp" method="get">
    <input type="submit" value="Registration" name="registration" />
</form>