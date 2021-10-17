<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ page import="ru.javawebinar.topjava.web.SecurityUtil" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals for ${SecurityUtil.getAuthUserId() == 1 ? "User" : "Admin"}</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt>From Date (included):</dt>
            <dd><input type="date" name="startDate" value="${startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date (included):</dt>
            <dd><input type="date" name="endDate" value="${endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time (included):</dt>
            <dd><input type="time" name="startTime" value="${startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time (excluded):</dt>
            <dd><input type="time" name="endTime" value="${endTime}"></dd>
        </dl>
        <button type="submit">Filter the meals</button>
    </form>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>