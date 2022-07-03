package com.ulawil.dietapp.food.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MealRepositorySql extends MealRepository, JpaRepository<Meal, Integer> {

    @Override
    @Query("select distinct m from Meal m join fetch m.ingredients")
    List<Meal> findAll();

    @Override
    @Query("select m from Meal m join fetch m.ingredients where m.name like %:name% and m.user.id=:id")
    List<Meal> findByNameContainsIgnoreCaseAndUserId(@Param("name") String name, @Param("id") Integer id);
}
