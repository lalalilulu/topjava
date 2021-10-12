<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<html lang="ru">
<head>
    <style>
        .isExcess {
            color: red;
        }
        .isNotExcess {
            color: green;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>All meals</h2>
<table cellpadding="7" border="2" width="100%">
    <thead>
    <tr>
        <th>Date & Time</th>
        <th>Meal's description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? 'isExcess' : 'isNotExcess'}">
            <td>${TimeUtil.formatDateTimeToString(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>