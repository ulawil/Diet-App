package com.ulawil.dietapp.food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {

    List<Food100g> findAll();

    Optional<Food100g> findById(Integer integer);

    Food100g save(Food100g entity);

    List<Food100g> findByNameContainsIgnoreCase(String name);

    List<Food100g> findUsersAndCommonFoodsByName(String name, int userId);
}
