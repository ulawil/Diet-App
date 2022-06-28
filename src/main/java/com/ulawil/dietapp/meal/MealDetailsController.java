package com.ulawil.dietapp.meal;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/mealDetails")
public class MealDetailsController {

    private final MealService mealService;

    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    String showMealDetails(@PathVariable("id") int mealId, Model model) {
        Meal mealDetails = mealService.findMealById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found")
        );
        model.addAttribute("mealDetails", mealDetails);
        return "mealDetails";
    }
}
