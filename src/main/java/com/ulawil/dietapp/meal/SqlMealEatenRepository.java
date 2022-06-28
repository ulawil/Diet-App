package com.ulawil.dietapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

    @Override
    @Query("select me from MealEaten me join fetch me.meal m join fetch m.ingredients i join fetch i.food100g where me.user.id=:id and me.dateEaten=:date")
    List<MealEaten> findByUserIdAndDateEaten(@Param("id") Integer id, @Param("date") LocalDate date);

    @Override
    @Query("select sum(i.grams) from MealEaten me join me.meal m join m.ingredients i join i.food100g f where me.user.id=:id and me.dateEaten=:date")
    Double findUsersTotalGramsByDate(@Param("id") Integer id, @Param("date") LocalDate date);

    @Override
    @Query("select sum(f.kcal*i.grams/100.) from MealEaten me join me.meal m join m.ingredients i join i.food100g f where me.user.id=:id and me.dateEaten=:date")
    Double findUsersTotalKcalByDate(@Param("id") Integer id, @Param("date") LocalDate date);

    @Override
    @Query("select sum(f.carbs*i.grams/100.) from MealEaten me join me.meal m join m.ingredients i join i.food100g f where me.user.id=:id and me.dateEaten=:date")
    Double findUsersTotalCarbsByDate(@Param("id") Integer id, @Param("date") LocalDate date);

    @Override
    @Query("select sum(f.protein*i.grams/100.) from MealEaten me join me.meal m join m.ingredients i join i.food100g f where me.user.id=:id and me.dateEaten=:date")
    Double findUsersTotalProteinByDate(@Param("id") Integer id, @Param("date") LocalDate date);

    @Override
    @Query("select sum(f.fat*i.grams/100.) from MealEaten me join me.meal m join m.ingredients i join i.food100g f where me.user.id=:id and me.dateEaten=:date")
    Double findUsersTotalFatByDate(@Param("id") Integer id, @Param("date") LocalDate date);
}
