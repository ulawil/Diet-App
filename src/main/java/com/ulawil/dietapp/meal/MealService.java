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
    private final FoodRepository foodRepository;
    private final UserService userService;

    public MealService(MealRepository mealRepository,
                       FoodRepository foodRepository,
                       UserService userService) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
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

    public List<Meal> findMealsByUserIdAndDateEaten(int userId, LocalDate date) {
        return mealRepository.findByUserIdAndDateEaten(userId, date);
    }

    public Double findTotalKcalByUserIdAndDateEaten(int userId, LocalDate date) {
        return mealRepository.findUsersTotalKcalByDate(userId, date);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }
}
