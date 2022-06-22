package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByNameContainsIgnoreCase(String name);
}
