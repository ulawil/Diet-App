package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.model.Ingredient;
import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.repository.FoodRepository;
import com.ulawil.dietapp.repository.MealRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/meals")
public class MealController {

    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    public MealController(MealRepository mealRepository,
                          FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
    }

    // REST endpoints

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Meal>> showMeals() {
        return ResponseEntity.ok(mealRepository.findAll());
    }

    // WEB endpoints
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showMeals(Model model) {
        model.addAttribute("meal", new Meal());
        return "meals";
    }

    @PostMapping(params = {"searchFood"})
    String searchFoods(@RequestParam("foodName") String foodName, Model model) {
        List<Food> foundFoods = foodRepository.findByNameContainsIgnoreCase(foodName);
        model.addAttribute("meal", new Meal());
        model.addAttribute("food", new Food());
        model.addAttribute("foundFoods", foundFoods);
        model.addAttribute("ingredient", new Ingredient());
        return "meals";
    }

    @PostMapping(params = {"addIngredient"})
    String addIngredient(@RequestBody Ingredient ingredient, Model model) {
        System.out.println(ingredient);
        return "meals";
    }
}
