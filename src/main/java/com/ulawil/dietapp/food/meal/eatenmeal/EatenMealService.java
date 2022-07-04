package com.ulawil.dietapp.food.meal.eatenmeal;

import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.FoodRepository;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.meal.Meal;
import com.ulawil.dietapp.food.meal.MealRepository;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EatenMealService {

    private final EatenMealRepository eatenMealRepository;
    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;
    private final UserService userService;

    public void addEatenMeal(int mealId, Double portion) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in!"));
        Meal mealToAdd = mealRepository.findById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal with given id not found!"));
        EatenMeal eatenMealToAdd = new EatenMeal();
        eatenMealToAdd.setMeal(mealToAdd);
        eatenMealToAdd.setUser(currentUser);
        if(portion == null) {
            portion = mealToAdd.getGrams();
        }
        eatenMealToAdd.setPortion(portion);

        eatenMealRepository.save(eatenMealToAdd);
    }

    public void addEatenFood(int foodId, Double portion) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in!"));
        Meal mealToAdd = new Meal();
        Food100g foodToAdd = foodRepository.findById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Food with given id not found!"));
        mealToAdd.getIngredients().add(new Ingredient(foodToAdd, portion));
        mealToAdd.setName(foodToAdd.getName());

        EatenMeal eatenMealToAdd = new EatenMeal();
        eatenMealToAdd.setUser(currentUser);
        eatenMealToAdd.setMeal(mealToAdd);
        eatenMealToAdd.setPortion(portion);

        mealRepository.save(mealToAdd);
        eatenMealRepository.save(eatenMealToAdd);
    }

    public void deleteEatenMeal(int mealEatenId) {
        EatenMeal eatenMealToDelete = eatenMealRepository.findById(mealEatenId).orElseThrow(
                () -> new IllegalArgumentException("Meal with given id not found!"));
        eatenMealToDelete.setUser(null);
        eatenMealToDelete.setMeal(null);
        eatenMealRepository.deleteById(mealEatenId);
    }

    public List<EatenMeal> findEatenMealsByUserIdAndDateEaten(Integer userId, LocalDate date) {
        return eatenMealRepository.findByUserIdAndDateEaten(userId, date);
    }
}
