package com.ulawil.dietapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

}
