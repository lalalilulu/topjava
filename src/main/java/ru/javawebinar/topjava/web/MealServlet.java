package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.InMemoryMealRepo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String DELETE_ACTION = "delete";
    private static final String UPDATE_ACTION = "update";
    private static final String CREATE_ACTION = "create";
    private MealRepo repo;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repo = new InMemoryMealRepo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "" : action.toLowerCase();
        switch (action) {
            case DELETE_ACTION:
                int id = getIdParam(request);
                log.info("Delete meal with id = {}", id);
                repo.delete(id);
                response.sendRedirect("meals");
                break;
            case CREATE_ACTION:
                log.info("Create a new meal");
                Meal newMeal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case UPDATE_ACTION:
                int mealId = getIdParam(request);
                log.info("Update meal with id = {}", mealId);
                Meal meal = repo.get(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            default:
                log.info("Get all meals");
                request.setAttribute("meals", MealsUtil.filteredByStreams(repo.getAll(), MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
        if (meal.getId() == null) {
            repo.create(meal);
        } else {
            repo.update(meal);
        }
        response.sendRedirect("meals");
    }

    private int getIdParam(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        return Integer.parseInt(idParam);
    }
}
