## MealRestController curl requests:

#### GET ALL MEALS
`curl http://localhost:8080/topjava/rest/meals`

#### FILTER
`curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00:00&endDate=2020-01-31&endTime=14:00:00"`

#### CREATE MEAL
`curl -X POST -H "Content-Type:application/json" -d "{ \"dateTime\" : \"2021-11-30T10:00:00\", \"description\" : \"New Food\", \"calories\":750}" http://localhost:8080/topjava/rest/meals`

#### GET MEAL
`curl http://localhost:8080/topjava/rest/meals/100003`

#### UPDATE MEAL
`curl -X PUT -H "Content-Type:application/json" -d "{ \"dateTime\" : \"2020-01-30T13:00:00\", \"description\" : \"Updated Food\", \"calories\":1300}" http://localhost:8080/topjava/rest/meals/100003`

#### DELETE MEAL
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100003`