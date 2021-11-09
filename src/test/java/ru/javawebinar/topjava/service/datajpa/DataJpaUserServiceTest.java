package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMeals() {
        User userWithMeals = service.getUserWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(userWithMeals, admin);
        MEAL_MATCHER.assertMatch(userWithMeals.getMeals(), adminMeal1, adminMeal2);
    }
}