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
        addAttributes(model);
        return "mealSummary";
    }

    @PostMapping(params = {"addMealEaten"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addEatenMeal(@RequestParam("addMealEaten") int mealId,
                        @RequestParam(name = "portion", required = false) Double portion,
                        @ModelAttribute("currentUser") User currentUser,
                        Model model) {
        eatenMealService.addEatenMeal(mealId, currentUser, portion);
        addAttributes(model);
        return "mealSummary";
    }
//
    @PostMapping(params = {"addEatenFood"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addEatenFood(@RequestParam("addEatenFood") int foodId,
                        @RequestParam(name = "portion", required = false) Double portion,
                        @ModelAttribute("currentUser") User currentUser,
                        Model model) {
        eatenMealService.addEatenFood(foodId, currentUser, portion);
        addAttributes(model);
        return "mealSummary";
    }

    @PostMapping(params = {"deleteMealEaten"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String deleteEatenMeal(@RequestParam("deleteMealEaten") int mealId, Model model) {
        eatenMealService.deleteEatenMeal(mealId);
        addAttributes(model);
        return "mealSummary";
    }

    @PostMapping(params = {"searchMeal"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchMeals(@RequestParam("mealName") String mealName,
                       @ModelAttribute("currentUser") User currentUser,
                       Model model) {
        List<Meal> foundMeals = mealService.findUsersMealsByName(mealName, currentUser.getId());
        model.addAttribute("foundMeals", foundMeals);
        return "mealSummary";
    }

    @PostMapping(params = {"searchFoods"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFoods(@RequestParam("foodName") String foodName,
                       @ModelAttribute("currentUser") User currentUser,
                       Model model) {
        List<Food100g> foundFoods = foodService.findUsersAndCommonFoodsByName(foodName, currentUser.getId());
        model.addAttribute("foundFoods", foundFoods);
        return "mealSummary";
    }

    @ModelAttribute
    void addAttributes(Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        List<EatenMeal> todaysMeals = eatenMealService.findEatenMealsByUserIdAndDateEaten(
                currentUser.getId(), LocalDate.now());
        MealStats todaysStats = new MealStats(
                todaysMeals.stream().map(em -> em.getMeal().getGrams()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getKcal()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getCarbs()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getProtein()).reduce(0., Double::sum),
                todaysMeals.stream().map(em -> em.getMeal().getFat()).reduce(0., Double::sum)
        );
        double totalGrams = todaysStats.getGrams();
        MealStats todaysGoals =  new MealStats (
                totalGrams,
                currentUser.getDailyKcalGoal(),
                currentUser.getDailyCarbsGoalPct() / 100. * totalGrams ,
                currentUser.getDailyProteinGoalPct() / 100. * totalGrams ,
                currentUser.getDailyFatGoalPct() / 100. * totalGrams
        );
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("todaysMeals", todaysMeals);
        model.addAttribute("todaysStats", todaysStats);
        model.addAttribute("todaysGoals", todaysGoals);
    }
}
