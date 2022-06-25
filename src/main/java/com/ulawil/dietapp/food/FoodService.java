package com.ulawil.dietapp.food;

import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> findFoodsByName(String foodName) {
        return foodRepository.findByNameContainsIgnoreCase(foodName);
    }

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }
}
