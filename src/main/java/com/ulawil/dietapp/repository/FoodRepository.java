package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findAll();
}
