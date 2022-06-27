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

    @PostMapping(params = {"addMealEaten"}, produces = MediaType.TEXT_HTML_VALUE)
    String addMeal(@RequestParam("addMealEaten") int mealId, Model model) {
        MealEaten addedMeal = mealService.addMealEaten(mealId, userService.getLoggedInUser());
        System.out.println("Added meal :" + addedMeal.getId());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @PostMapping(params = {"deleteMealEaten"}, produces = MediaType.TEXT_HTML_VALUE
    )
    String deleteMeal(@RequestParam("deleteMealEaten") int mealId, Model model) {
        System.out.println("deleting meal :" + mealId);
        mealService.deleteMealEaten(mealId, userService.getLoggedInUser());
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("totalKcal", todaysTotalKcal());
        return "todaysMeals";
    }

    @ModelAttribute
    List<MealEaten> todaysMeals() {
        return mealService.findUsersTodaysMealsEaten(userService.getLoggedInUser().getId());
    }

    @ModelAttribute
    Double todaysTotalKcal() {
        return mealService.findUsersTodaysTotalKcal(userService.getLoggedInUser().getId());
    }
}
