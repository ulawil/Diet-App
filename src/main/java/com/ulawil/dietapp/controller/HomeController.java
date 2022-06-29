package com.ulawil.dietapp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showHomePage(Model model) {
        return "index";
    }
}
