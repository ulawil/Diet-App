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

    public List<Food> findFoodsByName(String foodName) {
        return foodRepository.findByNameContainsIgnoreCase(foodName);
    }

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> findUsersAndCommonFoodsByName(String name, int userId) {
        return foodRepository.findUsersAndCommonFoodsByName(name, userId);
    }

    public Food saveFood(Food food, User currentUser) {
        if(currentUser.getRole() == UserRole.USER) {
            food.setUser(currentUser);
        }
        return foodRepository.save(food);
    }

    public Optional<Food> findFoodById(int foodId) {
        return foodRepository.findById(foodId);
    }
}
