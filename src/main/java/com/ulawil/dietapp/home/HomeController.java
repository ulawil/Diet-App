package com.ulawil.dietapp.home;

import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showHomePage() {
        return "index";
    }

    @ModelAttribute("currentUser")
    void currentUser(Model model) {
        User currentUser = userService.getCurrentUser().orElse(null);
        model.addAttribute("currentUser", currentUser);
    }
}
