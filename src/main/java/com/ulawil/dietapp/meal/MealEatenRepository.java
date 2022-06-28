package com.ulawil.dietapp.meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealEatenRepository {

    Optional<MealEaten> findById(Integer id);
    MealEaten save(MealEaten mealEaten);
    void deleteById(Integer id);
    List<MealEaten> findByUserIdAndDateEaten(Integer id, LocalDate date);
}
