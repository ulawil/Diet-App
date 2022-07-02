package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.DTO.UserDTO;
import com.ulawil.dietapp.service.RegistrationService;
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

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showRegistrationPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String register(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        registrationService.register(userDTO);
        model.addAttribute("message",
                "Registration complete! Please confirm your email before you can log in.");
        return "register";
    }

    @GetMapping(path = "/confirm")
    String cofirmRegistration(@RequestParam("token") String token, Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("emailConfirmed", true);
        // todo: handle resending confirmation token if expired
        model.addAttribute("message",
                "Email confirmed! You can now log in.");
        return "register";
    }
}