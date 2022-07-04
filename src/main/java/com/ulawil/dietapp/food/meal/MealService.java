package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.user.UserService;
import com.ulawil.dietapp.user.User;
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

    public Meal addMeal(Meal mealToSave) {
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        mealToSave.setUser(currentUser);
        return mealRepository.save(mealToSave);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }
}
