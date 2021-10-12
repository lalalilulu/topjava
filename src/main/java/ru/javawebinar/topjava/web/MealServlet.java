package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.MealRepoImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepo repo = new MealRepoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.info("Get all meals");
            request.setAttribute("meals", MealsUtil.filteredByStreams(repo.getAll(), MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getIdParam(request);
            log.info("Delete meal with id = {}", id);
            repo.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create")) {
            log.info("Create a new meal");
            Meal meal = new Meal(null, LocalDateTime.now(), "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        } else if (action.equals("update")) {
            int id = getIdParam(request);
            log.info("Update meal with id = {}", id);
            Meal meal = repo.get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
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
            repo.save(meal);
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
