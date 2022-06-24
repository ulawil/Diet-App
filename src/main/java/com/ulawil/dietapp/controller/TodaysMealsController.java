package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.service.UserService;
import com.ulawil.dietapp.service.MealService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/todaysMeals")
public class TodaysMealsController {

    private final UserService userService;
    private final MealService mealService;
    private final User currentUser;

    public TodaysMealsController(UserService userService, MealService mealService) {
        this.userService = userService;
        this.mealService = mealService;
        currentUser = userService.findUserById(1).get();
    }

    @GetMapping()
    String showUserPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", mealService.findUsersTodaysTotalKcal(currentUser.getId()));
        return "todaysMeals";
    }

    @PostMapping(params = {"searchMeal"})
    String searchMeals(@RequestParam("mealName") String foodName, Model model) {
        List<Meal> foundMeals = mealService.findUsersMealsByName(foodName, currentUser.getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("foundMeals", foundMeals);
        return "todaysMeals";
    }

    @PostMapping(params = {"addMeal"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addMeal(@RequestParam("addMeal") int mealId, Model model) {
        userService.addMealEaten(mealId, currentUser); // later get user from spring
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", mealService.findUsersTodaysTotalKcal(currentUser.getId()));
        return "todaysMeals";
    }

    @ModelAttribute
    List<Meal> todaysMeals() {
        return mealService.findUsersTodaysMeals(currentUser.getId());
    }
}
