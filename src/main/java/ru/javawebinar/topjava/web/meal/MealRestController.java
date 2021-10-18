package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredMealTos(Optional<LocalDate> startDateParam, Optional<LocalDate> endDateParam,
                                           Optional<LocalTime> startTimeParam, Optional<LocalTime> endTimeParam) {
        LocalDate startDate = startDateParam.orElse(LocalDate.MIN);
        LocalDate endDate = endDateParam.orElse(LocalDate.MAX);
        LocalTime startTime = startTimeParam.orElse(LocalTime.MIN);
        LocalTime endTime = endTimeParam.orElse(LocalTime.MAX);
        log.info("getFilteredMealTos with startDate: {}, endDate: {}, startTime: {}, endTime: {}", startDate, endDate, startTime, endTime);
        return MealsUtil.filterByPredicate(service.getAll(authUserId(), startDate, endDate), SecurityUtil.authUserCaloriesPerDay(),
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}