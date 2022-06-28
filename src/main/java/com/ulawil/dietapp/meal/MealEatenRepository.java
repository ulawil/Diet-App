package com.ulawil.dietapp.meal;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealEatenRepository {

    Optional<MealEaten> findById(Integer id);
    MealEaten save(MealEaten mealEaten);
    void deleteById(Integer id);
    List<MealEaten> findByUserIdAndDateEaten(Integer id, LocalDate date);

    Double findUsersTotalGramsByDate(Integer id, LocalDate date);
    Double findUsersTotalKcalByDate(Integer id, LocalDate date);
    Double findUsersTotalCarbsByDate(Integer id, LocalDate date);
    Double findUsersTotalProteinByDate(Integer id, LocalDate date);
    Double findUsersTotalFatByDate(Integer id, LocalDate date);
}
