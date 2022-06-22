package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food;
import com.ulawil.dietapp.repository.FoodRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    // REST endpoints

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Food>> showFoods() {
        return ResponseEntity.ok(foodRepository.findAll());
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    ResponseEntity<?> addFood(@RequestBody Food foodToAdd) {
        Food addedFood = foodRepository.save(foodToAdd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedFood.getId())
                .toUri();
        return ResponseEntity.created(location).body(addedFood);
    }

    // WEB endpoints

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showFoods(Model model) {
        model.addAttribute("food", new Food()); // has to be there for the post method
        return "foods";
    }

    @PostMapping(params = {"addFood"})
    String addFood(@ModelAttribute("food") Food foodToAdd, Model model) {
        foodRepository.save(foodToAdd);
        model.addAttribute("addedMessage", "Added an item!");
        return "foods";
    }

    @PostMapping(params = {"searchFood"})
    String search(@RequestParam("foodName") String foodName, Model model) {
        List<Food> foundFoods = foodRepository.findByNameContainsIgnoreCase(foodName);
        model.addAttribute("food", new Food());
        model.addAttribute("foundFoods", foundFoods);
        return "foods";
    }
}
