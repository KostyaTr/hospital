<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/logout" method="get">
    < <input type="submit" value="logout"/>
</form>

<br/>

<h1>Welcome back, <c:out value="${name}"/> </h1>

<br/>

<h3>All patients</h3>
<ul>
    <c:forEach items="${allPatients}" var="item">
        <li><c:out value="${item.firstName}  ${item.lastName}, doctor: ${item.appointedDoctor}"/></li>
    </c:forEach>
</ul>

<br/>

<h3>Your patients</h3>
<ul>
    <c:forEach items="${patients}" var="item">
        <li><c:out value="${item.firstName}  ${item.lastName}"/></li>
    </c:forEach>
</ul>

<br/>

<form action="${pageContext.request.contextPath}/personalDoctor" method="post">
    <label for="patientName">Имя пациента</label>
    <input id="patientName" type="text" name="patientName"><br/>

    <input type="submit" value="cure">
</form>