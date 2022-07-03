package com.ulawil.dietapp.food;

import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/food/add")
public class AddFoodController {

    private final FoodService foodService;
    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showFoodsPage(Model model) {
        model.addAttribute("food100g", new Food100g());
        return "addFood";
    }

    @PostMapping(params = {"addFood"},
                produces = MediaType.TEXT_HTML_VALUE,
                consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addFood(@ModelAttribute("food100g") @Valid Food100g foodToAdd, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errorMessage", "Cannot add food - make sure all data is valid!");
            return "addFood";
        }
        User currentUser = userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
        foodService.saveFood(foodToAdd, currentUser);
        model.addAttribute("addedMessage", "Food added!");
        return "addFood";
    }
}
