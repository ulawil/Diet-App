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
    private Meal mealToAdd = new Meal();

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
    String displayMealsPage(Model model) {
        mealToAdd = new Meal();
        model.addAttribute("meal", mealToAdd);
        return "meals";
    }

    @PostMapping(params = {"searchFood"})
    String searchFoods(@RequestParam("foodName") String foodName, Model model) {
        List<Food> foundFoods = foodRepository.findByNameContainsIgnoreCase(foodName);
        model.addAttribute("meal", mealToAdd);
        model.addAttribute("food", new Food());
        model.addAttribute("foundFoods", foundFoods);
        return "meals";
    }

    @PostMapping(params = {"addIngredient"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addIngredient(@RequestParam("addIngredient") int foodId,
                         @RequestParam("amount") int foodAmount,
                         Model model) {
        mealToAdd.addIngredient(new Ingredient(foodRepository.findById(foodId).get(), foodAmount));
        model.addAttribute("meal", mealToAdd);
        model.addAttribute("food", new Food());
        return "meals";
    }

    @PostMapping(params = {"createMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String createMeal(@RequestParam("mealName") String mealName, Model model) {
        mealToAdd.setName(mealName);
        mealRepository.save(mealToAdd);
        mealToAdd = new Meal();
        model.addAttribute("meal", mealToAdd);
        model.addAttribute("food", new Food());
        model.addAttribute("createdMessage", "Meal created!");
        return "meals";
    }
}
