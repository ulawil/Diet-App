package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealEaten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

    @Override
    @Query("select me from MealEaten me join fetch me.meal m join fetch m.ingredients i join fetch i.food100g where me.user.id=:id and me.dateEaten=:date")
    List<MealEaten> findByUserIdAndDateEaten(@Param("id") Integer id, @Param("date") LocalDate date);
}
