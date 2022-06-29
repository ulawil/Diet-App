package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealEaten;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealEatenRepository {

    Optional<MealEaten> findById(Integer id);
    MealEaten save(MealEaten mealEaten);
    void deleteById(Integer id);
    List<MealEaten> findByUserIdAndDateEaten(Integer id, LocalDate date);
    List<Object[]> findUsersTotalFoodStatsByDate(Integer id, LocalDate date);
}
