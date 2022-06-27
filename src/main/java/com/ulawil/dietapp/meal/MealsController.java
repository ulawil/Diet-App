package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.FoodService;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/meals")
public class MealsController {

    private final MealService mealService;
    private final FoodService foodService;
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Meal>> showMeals() {
        return ResponseEntity.ok(mealService.findAllMeals());
    }

    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    String showMealDetails(@PathVariable("id") int mealId, Model model) {
        Meal mealDetails = mealService.findMealById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found")
        );
        model.addAttribute("mealDetails", mealDetails);
        return "mealDetails";
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String displayMealsPage(Model model) {
        model.addAttribute("meal", mealService.getMealToAdd());
        return "meals";
    }

    @PostMapping(params = {"searchFood"})
    String searchFoods(@RequestParam("foodName") String foodName, Model model) {
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        model.addAttribute("foundFoods", foodService.findFoodsByName(foodName));
        return "meals";
    }

    @PostMapping(params = {"addIngredient"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addIngredient(@RequestParam("addIngredient") int foodId,
                         @RequestParam("amount") int foodAmount,
                         Model model) {
        mealService.addIngredientToMealToAdd(foodId, foodAmount);
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        return "meals";
    }

    @PostMapping(params = {"deleteIngredient"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addIngredient(@RequestParam("deleteIngredient") int ingId, Model model) {
        // todo
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        return "meals";
    }

    @PostMapping(params = {"createMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String createMeal(@RequestParam("mealName") String mealName, Model model) {
        mealService.AddMeal(mealName, userService.getLoggedInUser());
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        model.addAttribute("createdMessage", "Meal created!");
        return "meals";
    }

    @PostMapping(params = {"resetMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String resetMeal(Model model) {
        mealService.getMealToAdd().getIngredients().clear();
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        return "meals";
    }
}
