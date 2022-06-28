package com.ulawil.dietapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

    List<MealEaten> findByUserIdAndDateEaten(Integer id, LocalDate date);
}
