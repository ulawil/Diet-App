package com.ulawil.dietapp.meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealEatenRepository {

    MealEaten save(MealEaten mealEaten);

    List<MealEaten> findByUserIdIsAndDateEatenBetween(int userId, LocalDateTime dayBefore, LocalDateTime dayAfter);

    Optional<MealEaten> findById(int mealId);

    void deleteById(Integer id);
}
