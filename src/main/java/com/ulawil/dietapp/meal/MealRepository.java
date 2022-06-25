package com.ulawil.dietapp.meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealRepository {

    List<Meal> findAll();
    Optional<Meal> findById(int id);
    Meal save(Meal mealToAdd);
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String mealName, int userId);
    List<Meal> findByUserIdAndDateEatenBetween(int userId, LocalDateTime dayBefore, LocalDateTime dayAfter);
}
