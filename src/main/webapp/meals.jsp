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
<hr>
<a href="meals?action=create">Add meal</a>
<hr>
<table cellpadding="7" border="2" width="100%">
    <thead>
    <tr>
        <th>Date & Time</th>
        <th>Meal's description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? 'isExcess' : 'isNotExcess'}">
            <td>${TimeUtil.formatDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>