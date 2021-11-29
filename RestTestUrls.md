MealRestController Test Requests

GetAll - http://localhost:8080/topjava/rest/meals (method = get)

GetBetween - http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00:00&endDate=2020-01-31&endTime=14:00:00 (method = get)

Create - http://localhost:8080/topjava/rest/meals (method = post)

Created object:
{  
"dateTime": "2021-11-30T10:00:00",
"description": "New Food",
"calories": 750
}

Get - http://localhost:8080/topjava/rest/meals/100003 (method = get)

Update - http://localhost:8080/topjava/rest/meals/100003 (method = put)

Updated object:
{  
"dateTime": "2020-01-30T13:00:00",
"description": "Updated Food",
"calories": 1200
}

Delete - http://localhost:8080/topjava/rest/meals/100003 (method = delete)



