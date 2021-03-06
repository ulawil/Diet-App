package com.ulawil.dietapp.food.meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {

    List<Meal> findAll();
    Optional<Meal> findById(Integer id);
    Meal save(Meal mealToAdd);
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String name, Integer id);
}
