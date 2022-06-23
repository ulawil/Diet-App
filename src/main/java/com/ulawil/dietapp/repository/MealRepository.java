package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    @Override
    @Query("select distinct m from Meal m join fetch m.ingredients")
    List<Meal> findAll();

    List<Meal> findByNameContainsIgnoreCase(String mealName);
}
