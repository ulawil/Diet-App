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
    List<Meal> findByNameContainsIgnoreCaseAndUserId(String name, Integer id);

    @Query("select m from Meal m join fetch m.mealsEaten me where me.user.id=:userId and me.dateEaten=:date")
    List<Meal> findByUserIdAndDateEaten(@Param("userId") Integer id,
                                        @Param("date") LocalDate date);

    @Query("select sum(f.kcal*i.grams/100.) from User u join u.mealsEaten me join me.meal m join m.ingredients i join i.food100g f where u.id=:id and me.dateEaten=:date")
    Double findUsersTotalKcalByDate(@Param("id") Integer id,
                                    @Param("date") LocalDate date);
}
