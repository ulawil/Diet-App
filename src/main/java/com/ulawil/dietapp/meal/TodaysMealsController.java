package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/todaysMeals")
public class TodaysMealsController {

    private final UserService userService;
    private final MealService mealService;

    @GetMapping()
    String showUserPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @PostMapping(params = {"searchMeal"})
    String searchMeals(@RequestParam("mealName") String foodName, Model model) {
        List<Meal> foundMeals = mealService.findUsersMealsByName(foodName, userService.getLoggedInUser().getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("foundMeals", foundMeals);
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @PostMapping(params = {"addMeal"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addMeal(@RequestParam("addMeal") int mealId, Model model) {
        mealService.addMealEaten(mealId, userService.getLoggedInUser());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @ModelAttribute
    List<Meal> todaysMeals() {
        return mealService.findUsersTodaysMeals(userService.getLoggedInUser().getId());
    }

    @ModelAttribute
    Double todaysTotalKcal() {
        return mealService.findUsersTodaysTotalKcal(userService.getLoggedInUser().getId());
    }
}
