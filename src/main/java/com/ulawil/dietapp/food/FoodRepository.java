package com.ulawil.dietapp.food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {

    List<Food> findAll();

    Optional<Food> findById(Integer integer);

    Food save(Food entity);

    List<Food> findByNameContainsIgnoreCase(String name);

    List<Food> findUsersAndCommonFoodsByName(String name, int userId);
}
