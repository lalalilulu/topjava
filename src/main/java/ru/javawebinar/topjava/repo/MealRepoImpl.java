package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MealRepoImpl implements MealRepo {
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>(MealsUtil.meals.stream().collect(Collectors.toMap(Meal::getId, Function.identity())));
    private final AtomicInteger counter = new AtomicInteger(MealsUtil.meals.size());

    @Override
    public Meal get(int id) {
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(counter.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        return mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return mealsMap.remove(id) != null;
    }
}
