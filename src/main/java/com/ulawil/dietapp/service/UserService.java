package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.MealEaten;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.repository.MealEatenRepository;
import com.ulawil.dietapp.repository.MealRepository;
import com.ulawil.dietapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealEatenRepository mealEatenRepository;

    public UserService(UserRepository userRepository,
                       MealRepository mealRepository,
                       MealEatenRepository mealEatenRepository) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealEatenRepository = mealEatenRepository;
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public void addMealEaten(int mealId, User user) {
        Meal mealToAdd = mealRepository.findById(mealId).get(); // always present bc it's taken from the db
        MealEaten mealEaten = new MealEaten();
        mealEaten.setMeal(mealToAdd);
        mealEaten.setUser(user);
        mealEatenRepository.save(mealEaten);
    }
}
