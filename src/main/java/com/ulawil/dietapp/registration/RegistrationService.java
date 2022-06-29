package com.ulawil.dietapp.registration;

import com.ulawil.dietapp.model.UserDTO;
import com.ulawil.dietapp.model.UserRole;
import com.ulawil.dietapp.service.UserService;
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
        return userService.signUp(new UserDTO(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        ));
    }
}
