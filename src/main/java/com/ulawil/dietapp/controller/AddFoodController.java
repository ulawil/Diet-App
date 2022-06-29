package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.Food100g;
import com.ulawil.dietapp.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/addFood")
public class AddFoodController {

    private final FoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Food100g>> showFoods() {
        return ResponseEntity.ok(foodService.findAllFoods());
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    ResponseEntity<?> addFood(@RequestBody Food100g foodToAdd) {
        Food100g addedFood = foodService.saveFood(foodToAdd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedFood.getId())
                .toUri();
        return ResponseEntity.created(location).body(addedFood);
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showFoodsPage(Model model) {
        model.addAttribute("food100g", new Food100g()); // has to be there for the post method
        return "addFood";
    }

    @PostMapping(params = {"addFood"})
    String addFood(@ModelAttribute("food100g") @Valid Food100g foodToAdd, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errorMessage", "Cannot add food - make sure all data is valid!");
            return "addFood";
        }
        foodService.saveFood(foodToAdd);
        model.addAttribute("addedMessage", "Added an item!");
        return "addFood";
    }

    @PostMapping(params = {"searchFood"})
    String searchFoods(@RequestParam("foodName") String foodName, Model model) {
        model.addAttribute("food100g", new Food100g());
        model.addAttribute("foundFoods", foodService.findFoodsByName(foodName));
        return "addFood";
    }
}
