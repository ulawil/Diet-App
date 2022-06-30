package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.MealEaten;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.service.MealEatenService;
import com.ulawil.dietapp.service.MealService;
import com.ulawil.dietapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/todaysMeals")
public class TodaysMealsController {

    private final MealEatenService mealEatenService;
    private final UserService userService;
    private final MealService mealService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showTodaysMealsPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "todaysMeals";
    }

    @PostMapping(params = {"addMealEaten"}, produces = MediaType.TEXT_HTML_VALUE)
    String addMealEaten(@RequestParam("addMealEaten") int mealId, Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        mealEatenService.addMealEaten(mealId, currentUser);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "todaysMeals";
    }

    @PostMapping(params = {"deleteMealEaten"}, produces = MediaType.TEXT_HTML_VALUE
    )
    String deleteMealEaten(@RequestParam("deleteMealEaten") int mealId, Model model) {
        mealEatenService.deleteMealEaten(mealId);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "todaysMeals";
    }

    @PostMapping(params = {"searchMeal"})
    String searchMeals(@RequestParam("mealName") String foodName, Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        List<Meal> foundMeals = mealService.findUsersMealsByName(foodName, currentUser.getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        model.addAttribute("foundMeals", foundMeals);
        return "todaysMeals";
    }

    @ModelAttribute
    List<MealEaten> todaysMeals() {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        return mealEatenService.findMealsByUserIdAndDateEaten(
                currentUser.getId(), LocalDate.now());
    }

    @ModelAttribute
    List<Double> todaysMealsStats() {
        List<MealEaten> todaysMeals = todaysMeals();
        return List.of(
                todaysMeals.stream().map(MealEaten::getGrams).reduce(0., Double::sum),
                todaysMeals.stream().map(MealEaten::getKcal).reduce(0., Double::sum),
                todaysMeals.stream().map(MealEaten::getCarbs).reduce(0., Double::sum),
                todaysMeals.stream().map(MealEaten::getProtein).reduce(0., Double::sum),
                todaysMeals.stream().map(MealEaten::getFat).reduce(0., Double::sum)
        );
    }

    @ModelAttribute
    List<Double> goals() {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        Double totalGrams = todaysMealsStats().get(0);
        return List.of(currentUser.getDailyKcalGoal(),
                currentUser.getDailyCarbsGoalPct() * totalGrams / 100.,
                currentUser.getDailyProteinGoalPct() * totalGrams / 100.,
                currentUser.getDailyFatGoalPct() * totalGrams /100.
        );
    }
}
