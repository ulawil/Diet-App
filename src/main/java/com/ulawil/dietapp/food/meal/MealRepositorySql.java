package com.ulawil.dietapp.food.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MealRepositorySql extends MealRepository, JpaRepository<Meal, Integer> {

    @Override
    List<Meal> findAll();

    @Override
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String name, Integer id);
}
