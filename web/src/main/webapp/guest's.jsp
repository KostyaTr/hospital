<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Available doctors</h3>
<ul>
    <c:forEach items="${doctors}" var="item">
        <li><c:out value="${item.firstName} ${item.lastName} speciality: ${item.speciality}"/></li>
    </c:forEach>
</ul>

<br/>

<h3>Make an appointment</h3>
<form action="${pageContext.request.contextPath}/guestPage" method="post">
    <label for="firstName">имя</label>
    <input id="firstName" type="text" name="firstName"><br/>

    <label for="lastName">фамилия</label>
    <input id="lastName" type="text" name="lastName"><br/>

    <label for="email">email</label>
    <input id="email" type="text" name="email"><br/>

    <label for="phone">phone</label>
    <input id="phone" type="text" name="phone"><br/>

    <label for="doctorName">Имя доктора</label>
    <input id="doctorName" type="text" name="doctorName"><br/>

    <label for="visitingTime">Время визита</label>
    <input id="visitingTime" type="text" name="visitingTime">
    Пример: Tuesday, 16, 15:00

    <br/>
    <input type="submit" value="Confirm">
</form>

<br/>
<p style="color: red">${incorrectInput}</p>

<br/>

<form action="${pageContext.request.contextPath}/login" method="get">
    <input type="submit" value="Back to login">
</form>


