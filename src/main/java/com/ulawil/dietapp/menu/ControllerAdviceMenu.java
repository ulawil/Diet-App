package com.ulawil.dietapp.menu;

import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@AllArgsConstructor
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdviceMenu {

    private final UserService userService;

    @ModelAttribute
    public void addMenuAttributes(Model model) {
        User currentUser = userService.getCurrentUser().orElse(null);
        model.addAttribute("currentUser", currentUser);
    }
}
