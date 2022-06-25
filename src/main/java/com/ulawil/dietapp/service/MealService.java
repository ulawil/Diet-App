package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.*;
import com.ulawil.dietapp.repository.FoodRepository;
import com.ulawil.dietapp.repository.MealEatenRepository;
import com.ulawil.dietapp.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MealEatenRepository mealEatenRepository;
    private final FoodRepository foodRepository;
    private Meal mealToAdd = new Meal();

    public MealService(MealRepository mealRepository,
                       MealEatenRepository mealEatenRepository,
                       FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.mealEatenRepository = mealEatenRepository;
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

    public Integer findUsersTodaysTotalKcal(int userId) {
        Integer sum = 0;
        return findUsersTodaysMeals(userId).stream().map(Meal::getKcal).reduce(sum, Integer::sum);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }

    public void addMealEaten(int mealId, User user) {
        Meal mealToAdd = mealRepository.findById(mealId).get(); // always present bc it's taken from the db
        MealEaten mealEaten = new MealEaten();
        mealEaten.setMeal(mealToAdd);
        mealEaten.setUser(user);
        mealEatenRepository.save(mealEaten);
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
