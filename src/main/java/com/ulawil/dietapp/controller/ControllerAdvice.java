package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@AllArgsConstructor
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final UserService userService;

    @ModelAttribute
    public void addMenuAttributes(Model model) {
        User currentUser;
        try {
            currentUser = userService.getCurrentUser();
        } catch (IllegalStateException e) {
            currentUser = null;
        }
        model.addAttribute("currentUser", currentUser);
    }
}
