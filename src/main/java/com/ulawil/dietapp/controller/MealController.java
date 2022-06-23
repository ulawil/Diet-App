package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.repository.FoodRepository;
import com.ulawil.dietapp.repository.MealRepository;
import com.ulawil.dietapp.service.FoodService;
import com.ulawil.dietapp.service.MealService;
import com.ulawil.dietapp.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/meals")
public class MealController {

    private final MealService mealService;
    private final FoodService foodService;
    private final UserService userService;
    private final User currentUser;


    public MealController(MealService mealService, FoodService foodService, MealRepository mealRepository,
                          FoodRepository foodRepository,
                          UserService userService) {
        this.mealService = mealService;
        this.foodService = foodService;
        // todo: get current user from spring
        this.userService = userService;
        currentUser = userService.findUserById(1).get();
    }

    // REST endpoints

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Meal>> showMeals() {
        return ResponseEntity.ok(mealService.findAllMeals());
    }

    // WEB endpoints

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

    @PostMapping(params = {"createMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String createMeal(@RequestParam("mealName") String mealName, Model model) {
        mealService.AddMeal(mealName, currentUser);
        model.addAttribute("meal", mealService.getMealToAdd());
        model.addAttribute("food", new Food());
        model.addAttribute("createdMessage", "Meal created!");
        return "meals";
    }
}
