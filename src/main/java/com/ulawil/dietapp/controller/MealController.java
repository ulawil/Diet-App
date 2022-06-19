package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.repository.MealRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(path = "/meals")
public class MealController {

    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    ResponseEntity<List<Meal>> showMeals() {
        return ResponseEntity.ok(mealRepository.findAll());
    }
}
