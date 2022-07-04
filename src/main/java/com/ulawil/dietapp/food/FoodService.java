package com.ulawil.dietapp.food;

import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserRole;
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

    public List<Food100g> findUsersAndCommonFoodsByName(String name, int userId) {
        return foodRepository.findUsersAndCommonFoodsByName(name, userId);
    }

    public Food100g saveFood(Food100g food, User currentUser) {
        if(currentUser.getRole() == UserRole.USER) {
            food.setUser(currentUser);
        }
        return foodRepository.save(food);
    }

    public Optional<Food100g> findFoodById(int foodId) {
        return foodRepository.findById(foodId);
    }
}
