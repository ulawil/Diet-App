package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserService userService;

    public MealService(MealRepository mealRepository,
                       UserService userService) {
        this.mealRepository = mealRepository;
        this.userService = userService;
    }

    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> findMealById(int mealId) {
        return mealRepository.findById(mealId);
    }

    public Meal saveMeal(Meal mealToSave) {
        mealToSave.setUser(userService.getCurrentUser());
        return mealRepository.save(mealToSave);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }
}
