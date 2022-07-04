package com.ulawil.dietapp.registration;

import com.ulawil.dietapp.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showRegistrationPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String register(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            registrationService.register(userDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        model.addAttribute("message",
                "Registration complete! Please confirm your email before you can log in.");
        return "register";
    }

    @GetMapping(path = "/confirm")
    String cofirmRegistration(@RequestParam("token") String token, Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("emailConfirmed", true);
        try {
            registrationService.confirmToken(token);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        model.addAttribute("message",
                "Email confirmed! You can now log in.");
        return "register";
    }
}