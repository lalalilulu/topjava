package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserConst;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, UserConst.USER_ID));
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 16, 9, 0), "Завтрак админа 16.10.2021", 450), UserConst.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 16, 13, 30), "Обед админа 16.10.2021", 780), UserConst.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 16, 19, 30), "Ужин админа 16.10.2021", 1300), UserConst.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2021, Month.OCTOBER, 17, 16, 0), "Еда админа 17.10.2021", 550), UserConst.ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null ? userMeals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return CollectionUtils.isEmpty(userMeals) ? Collections.emptyList() :
                userMeals.values().stream()
                        .filter(meal -> DateTimeUtil.isBetweenClose(meal.getDate(), startDate, endDate))
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

