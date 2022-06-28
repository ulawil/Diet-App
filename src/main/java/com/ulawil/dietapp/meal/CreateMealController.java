package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.FoodService;
import com.ulawil.dietapp.ingredient.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@SessionAttributes("mealToCreate")
@RequestMapping(path = "/createMeal")
public class CreateMealController {

    private final MealService mealService;
    private final FoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Meal>> showMeals() {
        return ResponseEntity.ok(mealService.findAllMeals());
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showCreateMealPage(@ModelAttribute("mealToCreate") Meal mealToCreate, Model model) {
        model.addAttribute("mealToCreate", mealToCreate == null ? mealToCreate() : mealToCreate);
        return "createMeal";
    }

    @PostMapping(params = {"createMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String createMeal(@ModelAttribute("mealToCreate") Meal mealToCreate,
                      @RequestParam("mealName") String mealName, Model model) {
        mealToCreate.setName(mealName);
        mealService.saveMeal(mealToCreate);
        model.addAttribute("mealToCreate", mealToCreate());
        model.addAttribute("createdMessage", "Meal created!");
        return "createMeal";
    }

    @PostMapping(params = {"resetMeal"}, produces = MediaType.TEXT_HTML_VALUE)
    String resetMeal(@ModelAttribute("mealToCreate") Meal mealToCreate, Model model) {
        model.addAttribute("mealToCreate", mealToCreate());
        return "createMeal";
    }

    @PostMapping(params = {"searchFood"}, produces = MediaType.TEXT_HTML_VALUE)
    String searchFoods(@ModelAttribute("mealToCreate") Meal mealToCreate,
                       @RequestParam("foodName") String foodName, Model model) {
        model.addAttribute("mealToCreate", mealToCreate == null ? mealToCreate() : mealToCreate);
        model.addAttribute("food", new Food100g());
        model.addAttribute("foundFoods", foodService.findFoodsByName(foodName));
        return "createMeal";
    }

    @PostMapping(params = {"addIngredient"}, produces = MediaType.TEXT_HTML_VALUE)
    String addIngredient(@ModelAttribute("mealToCreate") Meal mealToCreate,
                         @RequestParam("addIngredient") int foodId,
                         @RequestParam("amount") double foodAmount,
                         Model model) {
        Ingredient ingToAdd = new Ingredient(foodService.findFoodById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Ingredient not found")), foodAmount);
        ingToAdd.setMeal(mealToCreate);
        mealToCreate.getIngredients().add(ingToAdd);
        return "createMeal";
    }

    @PostMapping(params = {"deleteIngredient"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addIngredient(@ModelAttribute("mealToCreate") Meal mealToCreate,
                         @RequestParam("deleteIngredient") int ingId, Model model) {
        // todo
        model.addAttribute("mealToCreate", mealToCreate == null ? mealToCreate() : mealToCreate);
        model.addAttribute("food", new Food100g());
        return "createMeal";
    }

    @ModelAttribute("mealToCreate")
    Meal mealToCreate() {
        return new Meal();
    }
}
