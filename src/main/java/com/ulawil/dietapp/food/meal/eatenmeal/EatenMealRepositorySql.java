package com.ulawil.dietapp.food.meal.eatenmeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface EatenMealRepositorySql extends EatenMealRepository, JpaRepository<EatenMeal, Integer> {

//    @Override
//    @Query("select distinct me from EatenMeal me join fetch me.meal m join fetch m.ingredients i join fetch i.food100g where me.user.id=:id and me.dateEaten=:date")
//    List<EatenMeal> findByUserIdAndDateEaten(@Param("id") Integer id, @Param("date") LocalDate date);
    @Override
    List<EatenMeal> findByUserIdAndDateEaten(Integer id, LocalDate date);
}
