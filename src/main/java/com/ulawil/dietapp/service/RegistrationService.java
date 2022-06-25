package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.RegistrationRequest;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final EmailValidationService emailValidationService;
    private final UserService userService;

    public String register(RegistrationRequest request) {
        boolean isValid = emailValidationService.test(request.getEmail());
        if(!isValid) {
            throw new IllegalStateException("Email not valid");
        }
        return userService.signUp(new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        ));
    }



}
