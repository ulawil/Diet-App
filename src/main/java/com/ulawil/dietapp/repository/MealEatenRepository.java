package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealEaten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealEatenRepository extends JpaRepository<MealEaten, Integer> {

}
