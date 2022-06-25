package com.ulawil.dietapp.registration;

import com.ulawil.dietapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String register(@ModelAttribute("user") User user) {
        registrationService.register(new RegistrationRequest(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()));
        return "todaysMeals";
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // todo: create user dto
        return "register";
    }


}
