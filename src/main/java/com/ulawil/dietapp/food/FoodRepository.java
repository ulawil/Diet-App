package com.ulawil.dietapp.food;

import com.ulawil.dietapp.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByNameContainsIgnoreCase(String name);
}

