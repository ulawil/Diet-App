package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@AllArgsConstructor
@Validated
@Controller
@RequestMapping(path = "/profile")
public class UserProfileController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> showCurrentUser() {
        return ResponseEntity.ok(currentUser());
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showUserProfile(Model model) {
        model.addAttribute("currentUser", currentUser());
        return "profile";
    }

    @PostMapping(params = {"newFirstName"}, produces = MediaType.TEXT_HTML_VALUE)
    String editFirstName(@RequestParam("newFirstName") @Valid String newFirstName, Model model) {
        User currentUser = currentUser();
        currentUser.setFirstName(newFirstName);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @PostMapping(params = {"newLastName"}, produces = MediaType.TEXT_HTML_VALUE)
    String editLastName(@RequestParam("newFirstName") @Valid String newLastName, Model model) {
        User currentUser = currentUser();
        currentUser.setFirstName(newLastName);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @PostMapping(params = {"newKcalGoal"}, produces = MediaType.TEXT_HTML_VALUE)
    String editKcalGoalForm(@RequestParam("newKcalGoal") @Valid double newKcalGoal, Model model) {
        User currentUser = currentUser();
        currentUser.setDailyKcalGoal(newKcalGoal);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @PostMapping(params = {"newCarbsGoal"}, produces = MediaType.TEXT_HTML_VALUE)
    String editCarbsGoal(@RequestParam("newCarbsGoal") @Valid double newCarbsGoal, Model model) {
        User currentUser = currentUser();
        currentUser.setDailyCarbsGoalPct(newCarbsGoal);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @PostMapping(params = {"newProteinGoal"}, produces = MediaType.TEXT_HTML_VALUE)
    String editProteinGoal(@RequestParam("newProteinGoal") @Valid double newProteinGoal, Model model) {
        User currentUser = currentUser();
        currentUser.setDailyProteinGoalPct(newProteinGoal);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @PostMapping(params = {"newFatGoal"}, produces = MediaType.TEXT_HTML_VALUE)
    String editFatGoal(@RequestParam("newFatGoal") @Valid double newFatGoal, Model model) {
        User currentUser = currentUser();
        currentUser.setDailyFatGoalPct(newFatGoal);
        User saved = userService.saveUser(currentUser);
        model.addAttribute("currentUser", saved);
        return "profile";
    }

    @ModelAttribute
    public User currentUser() {
        return userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolation(ConstraintViolationException e, Model model) {
        model.addAttribute("errorMessage", "Cannot edit the field - make sure the input is valid");
        model.addAttribute("currentUser", currentUser());
        return "profile";
    }
}
