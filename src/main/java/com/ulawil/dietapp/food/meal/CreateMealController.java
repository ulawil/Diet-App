package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.FoodService;
import com.ulawil.dietapp.food.ingredient.IngredientService;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Controller
@Validated
@SessionAttributes("mealToCreate")
@RequestMapping(path = "/meal/create")
public class CreateMealController {

    private final MealService mealService;
    private final IngredientService ingredientService;
    private final FoodService foodService;
    private final UserService userService;

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

    @PostMapping(params = {"createMeal"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String createMeal(@ModelAttribute("mealToCreate") Meal mealToCreate,
                      @RequestParam("mealName") @NotBlank(message = "Meal name cannot be empty") String mealName,
                      Model model) {
        mealToCreate.setName(mealName);
        mealService.saveMeal(mealToCreate);
        model.addAttribute("mealToCreate", mealToCreate());
        model.addAttribute("createdMessage", "Meal created!");
        return "createMeal";
    }

    @PostMapping(params = {"addIngredient"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addIngredient(@ModelAttribute("mealToCreate") Meal mealToCreate,
                         @RequestParam("addIngredient") int foodId,
                         @RequestParam("amount") @Min(value = 0) double foodAmount,
                         Model model) {
        Ingredient ingToAdd = new Ingredient(foodService.findFoodById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Ingredient not found")), foodAmount);
        if(mealToCreate.getIngredients().isEmpty()) {
            mealToCreate.setName("placeholder");
            mealToCreate.setAmount(0);
            mealService.saveMeal(mealToCreate);
        }
        mealToCreate.getIngredients().add(ingToAdd);
        ingToAdd.setMeal(mealToCreate);
        ingredientService.saveIngredient(ingToAdd);
        return "createMeal";
    }

    @PostMapping(params = {"deleteIngredient"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    String deleteIngredient(@ModelAttribute("mealToCreate") Meal mealToCreate,
                            @RequestParam("deleteIngredient") int ingId,
                            Model model) {
        Ingredient ingredientToDelete = ingredientService.findIngredientById(ingId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        ingredientToDelete.setMeal(null);
        mealToCreate.getIngredients().removeIf(ing -> ing.getId()==ingId);
        ingredientService.deleteIngredientById(ingId);
        return "createMeal";
    }

    @PostMapping(params = {"searchFood"},
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFoods(@ModelAttribute("mealToCreate") Meal mealToCreate,
                       @RequestParam("foodName") String foodName,
                       Model model) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        List<Food100g> foundFoods = foodService.findUsersAndCommonFoodsByName(currentUser.getId(), foodName);
        model.addAttribute("mealToCreate", mealToCreate);
        model.addAttribute("food", new Food100g());
        model.addAttribute("foundFoods", foundFoods);
        return "createMeal";
    }

    @ModelAttribute("mealToCreate")
    Meal mealToCreate() {
        Meal mealToCreate = new Meal();
        mealToCreate.setName("")
        return new Meal();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolation(ConstraintViolationException e,
                                     Model model,
                                     HttpServletRequest request) {
        model.addAttribute("errorMessage", "Cannot add ingredient - make sure the amount is valid");
        Meal mealToCreate = (Meal)request.getSession().getAttribute("mealToCreate");
        model.addAttribute("mealToCreate", mealToCreate);
        return "createMeal";
    }
}
