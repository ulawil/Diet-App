package com.ulawil.dietapp.meal;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository {

    List<Meal> findAll();
    Optional<Meal> findById(Integer id);
    Meal save(Meal mealToAdd);
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String name, Integer id);
    List<Meal> findByUserIdAndDateEaten(Integer id, LocalDate date);
    Double findUsersTotalKcalByDate(Integer id, LocalDate date);
}
