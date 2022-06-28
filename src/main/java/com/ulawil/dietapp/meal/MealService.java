package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.food.FoodRepository;
import com.ulawil.dietapp.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
