package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealCollector implements Collector<UserMeal, List<UserMealWithExcess>, List<UserMealWithExcess>> {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int caloriesPerDay;
    private final Map<LocalDate, Integer> caloriesPerDays = new HashMap<>();

    public UserMealCollector(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public Supplier<List<UserMealWithExcess>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExcess>, UserMeal> accumulator() {
        return (mealWithExcessList, meal) -> {
            mealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
            caloriesPerDays.merge(LocalDate.from(meal.getDateTime()), meal.getCalories(), Integer::sum);
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExcess>> combiner() {
        return (resultList, mealWithExcessList) -> {
            resultList.addAll(mealWithExcessList);
            return resultList;
        };
    }

    @Override
    public Function<List<UserMealWithExcess>, List<UserMealWithExcess>> finisher() {
        return (resultList) -> {
            resultList = resultList.stream()
                    .filter(mealWithExcess -> TimeUtil.isBetweenHalfOpen(mealWithExcess.getLocalTime(), startTime, endTime))
                    .collect(Collectors.toList());
                    resultList.forEach(mealWithExcess -> mealWithExcess.setExcess(caloriesPerDays.get(mealWithExcess.getLocalDate()) > caloriesPerDay));
            return resultList;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT));
    }
}
