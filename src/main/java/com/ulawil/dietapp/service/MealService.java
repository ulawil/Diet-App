package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.model.Ingredient;
import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.repository.FoodRepository;
import com.ulawil.dietapp.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;
    private Meal mealToAdd = new Meal();;

    public MealService(MealRepository mealRepository, FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
    }

    public Object getMealToAdd() {
        return mealToAdd;
    }

    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    public List<Meal> findUsersTodaysMeals(int userId) {
        LocalDateTime dayStart = LocalDate.now().atStartOfDay();
        LocalDateTime dayEnd = LocalDate.now().plusDays(1).atStartOfDay();
        return mealRepository.findByUserIdAndDateEatenBetween(userId, dayStart, dayEnd);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }


    public void addIngredientToMealToAdd(int foodId, int foodAmount) {
        Food food = foodRepository.findById(foodId).get(); // always present bc it's taken from the db
        mealToAdd.addIngredient(new Ingredient(food, foodAmount));
    }

    public void AddMeal(String mealName, User currentUser) {
        mealToAdd.setName(mealName);
        mealToAdd.setUser(currentUser);
        mealRepository.save(mealToAdd);
        mealToAdd = new Meal();
    }
}
