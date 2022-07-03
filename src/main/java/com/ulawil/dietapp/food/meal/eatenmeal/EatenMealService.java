package com.ulawil.dietapp.food.meal.eatenmeal;

import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.FoodRepository;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.meal.Meal;
import com.ulawil.dietapp.food.meal.MealRepository;
import com.ulawil.dietapp.user.User;
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

    public void addEatenMeal(int mealId, User user, Double portion) {
        Meal mealToAdd = mealRepository.findById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        EatenMeal eatenMealToAdd = new EatenMeal();
        eatenMealToAdd.setMeal(mealToAdd);
        eatenMealToAdd.setUser(user);
        if(portion == null) {
            portion = mealToAdd.getAmount();
        }
        eatenMealToAdd.setPortion(portion);
        eatenMealRepository.save(eatenMealToAdd);
    }

    public void addEatenFood(int foodId, User user, Double portion) {
        Meal mealToAdd = new Meal();
        Food100g foodToAdd = foodRepository.findById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Food not found"));
        mealToAdd.getIngredients().add(new Ingredient(foodToAdd, portion));

        EatenMeal eatenMealToAdd = new EatenMeal();
        eatenMealToAdd.setMeal(mealToAdd);
        eatenMealToAdd.setUser(user);
        eatenMealToAdd.setPortion(portion);
        mealRepository.save(mealToAdd);
        eatenMealRepository.save(eatenMealToAdd);
    }

    public void deleteEatenMeal(int mealEatenId) {
        EatenMeal eatenMealToDelete = eatenMealRepository.findById(mealEatenId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        eatenMealToDelete.setUser(null);
        eatenMealToDelete.setMeal(null);
        eatenMealRepository.deleteById(mealEatenId);
    }

    public List<EatenMeal> findEatenMealsByUserIdAndDateEaten(Integer userId, LocalDate date) {
        List<EatenMeal> ems =  eatenMealRepository.findByUserIdAndDateEaten(userId, date);
        System.out.println("Found " + ems.size() + " meals");
        return eatenMealRepository.findByUserIdAndDateEaten(userId, date);
    }
}
