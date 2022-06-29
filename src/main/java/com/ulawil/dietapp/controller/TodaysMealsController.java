package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.MealEaten;
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

    @GetMapping()
    String showTodaysMealsPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        addNutritionalInfoToModel(model);
        return "todaysMeals";
    }

    @PostMapping(params = {"addMealEaten"}, produces = MediaType.TEXT_HTML_VALUE)
    String addMealEaten(@RequestParam("addMealEaten") int mealId, Model model) {
        mealEatenService.addMealEaten(mealId, userService.getCurrentUser());
        model.addAttribute("todaysMeals", todaysMeals());
        addNutritionalInfoToModel(model);
        return "todaysMeals";
    }

    @PostMapping(params = {"deleteMealEaten"}, produces = MediaType.TEXT_HTML_VALUE
    )
    String deleteMealEaten(@RequestParam("deleteMealEaten") int mealId, Model model) {
        mealEatenService.deleteMealEaten(mealId);
        model.addAttribute("todaysMeals", todaysMeals());
        addNutritionalInfoToModel(model);
        return "todaysMeals";
    }

    @PostMapping(params = {"searchMeal"})
    String searchMeals(@RequestParam("mealName") String foodName, Model model) {
        List<Meal> foundMeals = mealService.findUsersMealsByName(foodName, userService.getCurrentUser().getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("foundMeals", foundMeals); addNutritionalInfoToModel(model);
        return "todaysMeals";
    }

    void addNutritionalInfoToModel(Model model) {
        model.addAttribute("totalGrams", todaysTotalGrams());
        model.addAttribute("totalKcal", todaysTotalKcal());
        model.addAttribute("totalCarbs", todaysTotalCarbs());
        model.addAttribute("totalProtein", todaysTotalProtein());
        model.addAttribute("totalFat", todaysTotalFat());
    }

    @ModelAttribute
    List<MealEaten> todaysMeals() {
        return mealEatenService.findMealsByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }

    @ModelAttribute
    Double todaysTotalGrams() {
        return mealEatenService.findTotalGramsByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }

    @ModelAttribute
    Double todaysTotalKcal() {
        return mealEatenService.findTotalKcalByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }

    @ModelAttribute
    Double todaysTotalCarbs() {
        return mealEatenService.findTotalCarbsByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }

    @ModelAttribute
    Double todaysTotalProtein() {
        return mealEatenService.findTotalProteinByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }

    @ModelAttribute
    Double todaysTotalFat() {
        return mealEatenService.findTotalFatByUserIdAndDateEaten(
                userService.getCurrentUser().getId(), LocalDate.now());
    }
}
