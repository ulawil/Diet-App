package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.Food100g;
import com.ulawil.dietapp.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food100g> findFoodsByName(String foodName) {
        return foodRepository.findByNameContainsIgnoreCase(foodName);
    }

    public List<Food100g> findAllFoods() {
        return foodRepository.findAll();
    }

    public Food100g saveFood(Food100g food) {
        return foodRepository.save(food);
    }

    public Optional<Food100g> findFoodById(int foodId) {
        return foodRepository.findById(foodId);
    }
}
