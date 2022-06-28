package com.ulawil.dietapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface SqlMealRepository extends MealRepository, JpaRepository<Meal, Integer> {

    @Override
    @Query("select m from Meal m join fetch m.ingredients i join fetch i.food100g")
    List<Meal> findAll();

    @Override
    @Query("select m from Meal m join fetch m.ingredients i join fetch i.food100g where m.name like %:name% and m.user.id=:id")
    List<Meal> findByNameContainsIgnoreCaseAndUserId(@Param("name") String name, @Param("id") Integer id);
}
