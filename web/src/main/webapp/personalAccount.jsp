<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${role == 'Guest'}">
    <%@ include file="guest's.jsp" %>
</c:if>

<c:if test="${authUser.role == 'AuthorizedUser'}">
    <%@ include file="user's.jsp" %>
</c:if>

<c:if test="${role == 'Doctor'}">
    <%@ include file="doctor's.jsp" %>
</c:if>