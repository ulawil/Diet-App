package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.user.UserService;
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
        model.addAttribute("totalGrams", todaysTotalGrams());
        model.addAttribute("totalKcal", todaysTotalKcal());
        model.addAttribute("totalCarbs", todaysTotalCarbs());
        model.addAttribute("totalProtein", todaysTotalProtein());
        model.addAttribute("totalFat", todaysTotalFat());
        return "todaysMeals";
    }

    @PostMapping(params = {"addMealEaten"}, produces = MediaType.TEXT_HTML_VALUE)
    String addMeal(@RequestParam("addMealEaten") int mealId, Model model) {
        MealEaten addedMeal = mealEatenService.addMealEaten(mealId, userService.getCurrentUser());
        System.out.println("Added meal :" + addedMeal.getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @PostMapping(params = {"deleteMealEaten"}, produces = MediaType.TEXT_HTML_VALUE
    )
    String deleteMeal(@RequestParam("deleteMealEaten") int mealId, Model model) {
        mealEatenService.deleteMealEaten(mealId);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @PostMapping(params = {"searchMeal"})
    String searchMeals(@RequestParam("mealName") String foodName, Model model) {
        List<Meal> foundMeals = mealService.findUsersMealsByName(foodName, userService.getCurrentUser().getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("foundMeals", foundMeals);
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
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
