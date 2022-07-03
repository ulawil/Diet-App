package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.food.meal.eatenmeal.EatenMeal;
import com.ulawil.dietapp.food.meal.eatenmeal.EatenMealService;
import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.FoodService;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "/meals/summary")
@AllArgsConstructor
public class MealSummaryController {

    private final EatenMealService eatenMealService;
    private final MealService mealService;
    private final UserService userService;
    private final FoodService foodService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showMealSummaryPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "mealSummary";
    }

    @PostMapping(params = {"addMealEaten"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addEatenMeal(@RequestParam("addMealEaten") int mealId,
                        @RequestParam(name = "portion", required = false) Double portion,
                        Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        eatenMealService.addEatenMeal(mealId, currentUser, portion);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "mealSummary";
    }

    @PostMapping(params = {"addEatenFood"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addEatenFood(@RequestParam("addEatenFood") int foodId,
                        @RequestParam(name = "portion", required = false) Double portion,
                        Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        eatenMealService.addEatenFood(foodId, currentUser, portion);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "mealSummary";
    }

    @PostMapping(params = {"deleteMealEaten"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String deleteEatenMeal(@RequestParam("deleteMealEaten") int mealId, Model model) {
        eatenMealService.deleteEatenMeal(mealId);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        return "mealSummary";
    }

    @PostMapping(params = {"searchMeal"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchMeals(@RequestParam("mealName") String mealName, Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        List<Meal> foundMeals = mealService.findUsersMealsByName(mealName, currentUser.getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        model.addAttribute("foundMeals", foundMeals);
        return "mealSummary";
    }

    @PostMapping(params = {"searchFoods"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFoods(@RequestParam("foodName") String foodName, Model model) {
        List<Food100g> foundFoods = foodService.findFoodsByName(foodName);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("todaysMealsStats", todaysMealsStats());
        model.addAttribute("goals", goals());
        model.addAttribute("foundFoods", foundFoods);
        return "mealSummary";
    }

    @ModelAttribute
    List<EatenMeal> todaysMeals() {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        return eatenMealService.findEatenMealsByUserIdAndDateEaten(
                currentUser.getId(), LocalDate.now());
    }

    @ModelAttribute
    List<Double> todaysMealsStats() {
        List<EatenMeal> todaysMeals = todaysMeals();
        return List.of(
                todaysMeals.stream().map(em -> em.getMeal().getAmount()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getKcal()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getCarbs()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getProtein()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getFat()).reduce(0., Double::sum)
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
