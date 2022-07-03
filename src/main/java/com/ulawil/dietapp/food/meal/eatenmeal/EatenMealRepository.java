package com.ulawil.dietapp.food.meal.eatenmeal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EatenMealRepository {

    Optional<EatenMeal> findById(Integer id);
    EatenMeal save(EatenMeal mealEaten);
    void deleteById(Integer id);
    List<EatenMeal> findByUserIdAndDateEaten(Integer id, LocalDate date);
}
