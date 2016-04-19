<%-- 
    Document   : History
    Created on : Apr 4, 2016, 2:40:49 PM
    Author     : ryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@page import="business.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Credit Card Log</title>
    </head>
    <body>
        <h1>Credit Card Log Entries for:${card.accountId}</h1>
        <p>
            <c:forEach items="${card.creditHistory}" var="h">
                <c:out value="${h}"/>
            </c:forEach>
        </p>
    </body>
</html>
