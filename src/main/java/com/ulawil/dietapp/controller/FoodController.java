package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.repository.FoodRepository;
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
@RequestMapping(path = "/foods")
public class FoodController {

    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping()
    ResponseEntity<List<Food>> showFoods() {
        return ResponseEntity.ok(foodRepository.findAll());
    }

    @PostMapping
    ResponseEntity<?> addFood(@RequestBody Food foodToAdd) {
        Food addedFood = foodRepository.save(foodToAdd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedFood.getId())
                .toUri();
        return ResponseEntity.created(location).body(addedFood);
    }
}
