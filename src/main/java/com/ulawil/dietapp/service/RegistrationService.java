package com.ulawil.dietapp.service;

import com.ulawil.dietapp.email.EmailSender;
import com.ulawil.dietapp.model.ConfirmationToken;
import com.ulawil.dietapp.model.DTO.UserDTO;
import com.ulawil.dietapp.repository.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidationService emailValidationService;
    private final EmailSender emailSender;

    public String register(UserDTO userDTO) {
        boolean isValid = emailValidationService.test(userDTO.getEmail());
        if (!isValid) {
            throw new IllegalStateException("Email not valid");
        }
        String token = userService.signUp(userDTO);
        String link = "http://localhost:8080/register/confirm?token=" + token;
        emailSender.send(userDTO.getEmail(), buildEmail(userDTO.getFirstName(), link));
        return token;
    }


    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div>" +
                "<p>Hi " + name + "! Thank you for registering. Please click on the below link to activate your account:</p>" +
                "<a href=\"" + link + "\">Activate Now</a>" +
                "<p>Link will expire in 15 minutes. See you soon!</p>" +
                "</div>";
    }
}
