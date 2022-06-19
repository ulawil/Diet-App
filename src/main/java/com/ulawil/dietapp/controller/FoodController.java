package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.repository.FoodRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FoodController {

    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping(path = "/foods")
    ResponseEntity<List<Food>> showFoods() {
        return ResponseEntity.ok(foodRepository.findAll());
    }
}
