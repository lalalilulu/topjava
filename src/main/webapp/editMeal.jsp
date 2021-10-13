<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Create or edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<hr>
<h2>${meal.id == null ? "Add meal" : "Edit meal"}</h2>
<hr>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    Data & Time: <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <hr>
    Meal's description: <input type="text" name="description" value="${meal.description}">
    <hr>
    Calories: <input type="number" name="calories" value="${meal.calories}">
    <hr>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>