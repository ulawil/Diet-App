package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealEaten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

}
