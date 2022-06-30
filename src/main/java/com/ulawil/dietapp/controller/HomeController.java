package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showHomePage() {
        return "index";
    }
}
