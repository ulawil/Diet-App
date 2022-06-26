package com.ulawil.dietapp.registration;

import com.ulawil.dietapp.user.UserDTO;
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
    String register(@ModelAttribute("userDTO") UserDTO userDTO) {
        registrationService.register(new RegistrationRequest(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword()));
        return "registered";
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }
}
