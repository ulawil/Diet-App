package com.ulawil.dietapp.user;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/profile")
@AllArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showUserProfile(Model model) {
        model.addAttribute("currentUser", currentUser());
        return "profile";
    }

    @ModelAttribute
    public User currentUser() {
        return userService.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("No user currently logged in"));
    }
}
