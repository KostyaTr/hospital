<%@ page  contentType="text/html; charset=Cp1251" pageEncoding="Cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>Регистрация</h3>
<form action="${pageContext.request.contextPath}/signUp" method="post">
    <label for="login">логин</label>
    <input id="login" type="text" name="login"><br/>

    <label for="firstName">имя</label>
    <input id="firstName" type="text" name="firstName"><br/>

    <label for="lastName">фамилия</label>
    <input id="lastName" type="text" name="lastName"><br/>

    <label for="email">email</label>
    <input id="email" type="text" name="email"><br/>

    <label for="phone">phone</label>
    <input id="phone" type="text" name="phone"><br/>

    <label for="password">password</label>
    <input id="password" type="password" name="password">

    <label for="passwordRepeat">Repeat your password</label>
    <input id="passwordRepeat" type="password" name="passwordRepeat">

    <input type="submit">
</form>


<p style="color: red">${loginError}</p>

<p style="color: red">${passwordError}</p>