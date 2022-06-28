package com.ulawil.dietapp.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food100g, Integer> {
    List<Food100g> findByNameContainsIgnoreCase(String name);
}

