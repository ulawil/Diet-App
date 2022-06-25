package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealRepository {

    List<Meal> findAll();
    Optional<Meal> findById(int id);
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String mealName, int userId);
    List<Meal> findByUserIdAndDateEatenBetween(int userId, LocalDateTime dayBefore, LocalDateTime dayAfter);
    Meal save(Meal mealToAdd);
}
