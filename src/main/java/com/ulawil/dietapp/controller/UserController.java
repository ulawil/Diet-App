package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<User> showCurrentUser() {
        return ResponseEntity.ok(userService.getLoggedInUser());
    }
}
