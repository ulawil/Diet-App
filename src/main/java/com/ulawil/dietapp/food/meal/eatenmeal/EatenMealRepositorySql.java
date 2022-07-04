package com.ulawil.dietapp.food.meal.eatenmeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface EatenMealRepositorySql extends EatenMealRepository, JpaRepository<EatenMeal, Integer> {

    @Override
    List<EatenMeal> findByUserIdAndDateEaten(Integer id, LocalDate date);
}
