package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MealEatenService {

    private final MealEatenRepository mealEatenRepository;
    private final MealRepository mealRepository;

    public MealEaten addMealEaten(int mealEatenId, User user) {
        Meal mealToAdd = mealRepository.findById(mealEatenId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        MealEaten mealEatenToAdd = new MealEaten();
        mealEatenToAdd.setMeal(mealToAdd);
        mealEatenToAdd.setUser(user);
        return mealEatenRepository.save(mealEatenToAdd);
    }

    public void deleteMealEaten(int mealEatenId) {
        MealEaten mealEatenToDelete = mealEatenRepository.findById(mealEatenId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        mealEatenToDelete.setUser(null);
        mealEatenToDelete.setMeal(null);
        mealEatenRepository.deleteById(mealEatenId);
    }

    public List<MealEaten> findMealsByUserIdAndDateEaten(Integer userId, LocalDate date) {
        return mealEatenRepository.findByUserIdAndDateEaten(userId, date);
    }
}
