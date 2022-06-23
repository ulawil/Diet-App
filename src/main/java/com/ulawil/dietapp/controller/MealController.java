package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.model.Ingredient;
import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.repository.FoodRepository;
import com.ulawil.dietapp.repository.MealRepository;
import com.ulawil.dietapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService;
    private final User currentUser;


    public MealController(MealRepository mealRepository,
                          FoodRepository foodRepository,
                          UserService userService) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
        // todo: get current user from spring
        this.userService = userService;
        currentUser = userService.findUserById(1).get();
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
        Food foodToIngredient = foodRepository.findById(foodId).get(); // always present bc it's taken from the db
        mealToAdd.addIngredient(new Ingredient(foodToIngredient, foodAmount));
        model.addAttribute("meal", mealToAdd);
        model.addAttribute("food", new Food());
        return "meals";
    }

    @PostMapping(params = {"createMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String createMeal(@RequestParam("mealName") String mealName, Model model) {
        mealToAdd.setName(mealName);
        mealToAdd.setUser(currentUser);
        mealRepository.save(mealToAdd);
        mealToAdd = new Meal();
        model.addAttribute("meal", mealToAdd);
        model.addAttribute("food", new Food());
        model.addAttribute("createdMessage", "Meal created!");
        return "meals";
    }
}
