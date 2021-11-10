package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User userWithMeals = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(userWithMeals, admin);
        MEAL_MATCHER.assertMatch(userWithMeals.getMeals(), adminMeal2, adminMeal1);
    }

    @Test
    public void getWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(NOT_FOUND));
    }

    @Test
    public void getWithoutMeals() {
        User newUser = UserTestData.getNew();
        User createdUser = service.create(newUser);
        newUser.setId(createdUser.getId());
        User userWithoutMeals = service.getWithMeals(createdUser.getId());
        USER_MATCHER.assertMatch(userWithoutMeals, newUser);
        assertTrue(userWithoutMeals.getMeals().isEmpty());
    }
}