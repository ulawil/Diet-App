package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.MealEaten;
import com.ulawil.dietapp.repository.MealEatenRepository;
import com.ulawil.dietapp.repository.MealRepository;
import com.ulawil.dietapp.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MealEatenService {

    private final MealEatenRepository mealEatenRepository;
    private final MealRepository mealRepository;

    public MealEaten addMealEaten(int mealId, User user, Double portion) {
        Meal mealToAdd = mealRepository.findById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        MealEaten mealEatenToAdd = new MealEaten();
        mealEatenToAdd.setUser(user);
        mealEatenToAdd.setMeal(mealToAdd);
        if(portion == null) {
            portion = mealToAdd.getGrams();
        }
        mealEatenToAdd.setPortion(portion);
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
