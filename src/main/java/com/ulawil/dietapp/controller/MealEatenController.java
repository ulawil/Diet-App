package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.MealEaten;
import com.ulawil.dietapp.repository.MealEatenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mealsEaten")
public class MealEatenController {

    private final MealEatenRepository mealEatenRepository;

    public MealEatenController(MealEatenRepository mealEatenRepository) {
        this.mealEatenRepository = mealEatenRepository;
    }

    @GetMapping
    ResponseEntity<List<MealEaten>> readAllMealsEaten() {
        return ResponseEntity.ok(mealEatenRepository.findAll());
    }
}
