<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/logout" method="get">
     <input type="submit" value="logout"/>
</form>

<br/>

<h1>Welcome back, <c:out value="${name}"/> </h1>

<br/>

<h3>Available doctors</h3>
<ul>
    <c:forEach items="${doctors}" var="item">
        <li><c:out value="${item.firstName} ${item.lastName} speciality: ${speciality}"/></li>
    </c:forEach>
</ul>

<br/>

<h3>Your appointments</h3>
<ul>
    <c:forEach items="${appointments}" var="item">
        <li><c:out value="${item.appointedDoctor} at ${item.visitingTime}"/></li>
    </c:forEach>
</ul>

<br/>

<h3>Make an appointment</h3>
<form action="${pageContext.request.contextPath}/personalUser" method="post">
    <label for="doctorName">Имя доктора</label>
    <input id="doctorName" type="text" name="doctorName"><br/>

    <label for="visitingTime">Время визита</label>
    <input id="visitingTime" type="text" name="visitingTime">
    Пример: Tuesday, 16, 15:00
    <br/>

    <input type="submit" value="Confirm">
</form>