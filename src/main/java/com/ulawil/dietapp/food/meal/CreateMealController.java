package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.FoodService;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.ingredient.IngredientService;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping(path = "/meal/create")
@SessionAttributes("ingredients")
@Validated
@AllArgsConstructor
public class CreateMealController {

    private final MealService mealService;
    private final IngredientService ingredientService;
    private final FoodService foodService;
    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showCreateMealPage(@ModelAttribute("ingredients") List<Ingredient> ingredients, Model model) {
        model.addAttribute("mealToCreate", mealToCreate());
        model.addAttribute("ingredients", ingredients == null ? ingredients(): ingredients);
        model.addAttribute("mealStats", ingredients == null ? new Meal() : mealStats(model));
        return "createMeal";
    }

    @PostMapping(params = {"createMeal"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String createMeal(@ModelAttribute("mealToCreate") Meal mealToCreate,
                      @ModelAttribute("ingredients") List<Ingredient> ingredients,
                      BindingResult bindingResult,
                      Model model) {
        if(bindingResult.hasErrors()) {
            return "createMeal";
        }
        ingredients.forEach(ingredient -> ingredient.setMeal(mealToCreate));
        mealToCreate.setIngredients(new HashSet<>(ingredients));
        try {
            mealService.addMeal(mealToCreate);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        ingredients.clear();
        addModelAttributesWithDefaultValues(ingredients, model);
        model.addAttribute("createdMessage", "Meal created!");
        return "createMeal";
    }

    @PostMapping(params = {"addIngredient"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addIngredient(@ModelAttribute("ingredients") List<Ingredient> ingredients,
                         @RequestParam("addIngredient") int foodId,
                         @RequestParam("foodAmount") @Min(value = 0) double foodAmount,
                         Model model) {
        Ingredient ingredientToAdd = new Ingredient(foodService.findFoodById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Ingredient not found")), foodAmount);
        ingredients.add(ingredientToAdd);
        addModelAttributesWithDefaultValues(ingredients, model);
        return "createMeal";
    }

    @PostMapping(params = {"deleteIngredient"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    String deleteIngredient(@ModelAttribute("ingredients") List<Ingredient> ingredients,
                            @RequestParam("deleteIngredient") int ingredientId,
                            Model model) {
        ingredients.removeIf(ingredient -> ingredient.getId()== ingredientId);
        ingredientService.deleteIngredientById(ingredientId);
        addModelAttributesWithDefaultValues(ingredients, model);
        return "createMeal";
    }

    @PostMapping(params = {"searchFood"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFoods(@ModelAttribute("ingredients") List<Ingredient> ingredients,
                       @RequestParam("foodName") String foodName,
                       Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        List<Food> foundFoods = foodService.findUsersAndCommonFoodsByName(foodName, currentUser.getId());
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("mealStats", mealStats(model));
        model.addAttribute("foundFoods", foundFoods);
        return "createMeal";
    }

    @ModelAttribute("ingredients")
    List<Ingredient> ingredients() {
        return new ArrayList<>();
    }

    @ModelAttribute("mealToCreate")
    Meal mealToCreate() {
        return new Meal();
    }

    @ModelAttribute("mealStats")
    MealStats mealStats(Model model) {
        List<Ingredient> ingredients = (List<Ingredient>)model.getAttribute("ingredients");
        if(ingredients == null) {
            return new MealStats();
        }
        return new MealStats(
            ingredients.stream().map(Ingredient::getGrams).reduce(0., Double::sum),
            ingredients.stream().map(i -> i.getNutritionalInfo().getKcal()).reduce(0., Double::sum),
            ingredients.stream().map(i -> i.getNutritionalInfo().getCarbs()).reduce(0., Double::sum),
            ingredients.stream().map(i -> i.getNutritionalInfo().getProtein()).reduce(0., Double::sum),
            ingredients.stream().map(i -> i.getNutritionalInfo().getProtein()).reduce(0., Double::sum)
        );
    }

    @ModelAttribute("currentUser")
    void currentUser(Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        model.addAttribute("currentUser", currentUser);
    }

    private void addModelAttributesWithDefaultValues(List<Ingredient> ingredients, Model model) {
        model.addAttribute("mealToCreate", mealToCreate());
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("mealStats", mealStats(model));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolation(ConstraintViolationException e, Model model) {
        model.addAttribute("errorMessage", "Cannot add the ingredient - make sure the amount is right!");
        List<Ingredient> ingredients = (List<Ingredient>)model.getAttribute("ingredients");
        addModelAttributesWithDefaultValues(ingredients, model);
        return "createMeal";
    }
}
